/**
 * @author MarkHuang
 * @since  2018/3/08
 */
(function ($) {
    //所有table element 最外層的div
    const $APPEND_TABLE = $('#append_table');
    const JSON_OBJECT_KEY = ['id', 'value', 'dataType', 'beforeScript'];
    let testCaseName = '';
    let pageUrl = [];
    //紀錄每一頁的table element
    let $dataTable = [];
    //紀錄page中table的行數
    let countDataTableRow = [];
    //有幾頁,所謂的page這邊不是真的分頁,只是複數table的隱藏與顯示,且一個table對應一個page,一次只顯示一個table
    let pageCount = 0;
    let nowDisplayPage = 0;
    //紀錄在產生json data時所遇到的錯誤
    let generateJsonDataErrorMsg = [];

    /**
     * 在頁面載入時初始化頁面
     */
    (function initPage() {
        newPage(true);
        displayPage(pageCount);
        $('.page_btn').eq(nowDisplayPage - 1).addClass('page-active');
    }());

    /**
     * 新增一個page,並隱藏其他page的table
     * @param initFirst 初始化每一個table的第一行
     * @param isAfter   頁面試添加在當前table之後還是所有table之後
     */
    function newPage(initFirst = false, isAfter = false) {
        let pageIndex = 0;
        if (isAfter) {
            $dataTable[nowDisplayPage - 1].parent().after(createTableElement());
            resetAllPage();
            pageIndex = nowDisplayPage + 1;
        } else {
            $APPEND_TABLE.append(createTableElement());
            pageIndex = pageCount;
        }
        $dataTable[pageIndex - 1] = $(`.page_tab`).eq(pageIndex - 1).find('tbody');
        if (initFirst) {
            $dataTable[pageIndex - 1].append(createTableDataElement(pageIndex));
        }
        $('#append_page_number').before(
            `<li class="my-pagination-item page_btn cursor-point" page="${pageCount}">
                <span class=" my-pagination-link ">
                    ${pageCount}
                </span>
            </li>`);
    }

    /**
     * 顯示傳入的page
     * @param page
     */
    function displayPage(page) {
        $(`table`).addClass('hide');
        $(`.page_tab`).eq(page - 1).removeClass('hide');
        clearAllPageAction();
        $('.page_btn').eq(page - 1).addClass('page-active');
        nowDisplayPage = page;
    }

    /**
     * 刪除目前所有的page,並初始化所有的參數
     */
    function deleteAllPage() {
        $('table').remove();
        $('.page_btn').remove();
        $dataTable = [];
        pageUrl = [];
        countDataTableRow = [];
        pageCount = 0;
        nowDisplayPage = 0;
    }

    /**
     * 刪除頁面 下方所有page編號的hover效果
     */
    function clearAllPageAction() {
        $('.page_btn').removeClass('page-active');
    }

    /**
     * 重置所有的page,table與page的對應關係完全刷新
     */
    function resetAllPage() {
        $dataTable = [];
        countDataTableRow = [];
        pageCount = 0;
        const $TABLE = $APPEND_TABLE.find('table');
        const $PAGE_NUMBER = $('.page_btn');
        $APPEND_TABLE.find('tbody').each((index, me) => {
            pageCount++;
            $TABLE.eq(index).attr('id', 'page_' + (index + 1));
            countDataTableRow.push($(me).find('tr').length);
            $dataTable.push($(me));
            $PAGE_NUMBER.eq(index).attr('page', pageCount);
            $PAGE_NUMBER.eq(index).find('.my-pagination-link').text(index + 1);
        });
    }

    /**
     * 重置table中所有的列的編號
     */
    function resetTableRowOrder() {
        countDataTableRow[nowDisplayPage - 1] = 0;
        $dataTable[nowDisplayPage - 1].find('tr').each((index, me) => {
            $(me).find('td:first').text(index + 1);
            countDataTableRow[nowDisplayPage - 1] = index + 1;
        });
    }

    /**
     * table顯示p元素還是input元素
     * @param isInput input元素是否顯示
     */
    function displayTableSpan(isInput = true) {
        const TBODY = $('tbody');
        if (isInput) {
            TBODY.addClass('hide-odd-span');
            TBODY.removeClass('hide-even-span');
        } else {
            TBODY.addClass('hide-even-span');
            TBODY.removeClass('hide-odd-span');
        }
    }

    /**
     * 清空所有table的所有列的focus效果
     */
    function clearPageFocusRow() {
        if ((!window.event.ctrlKey && !window.event.shiftKey)) {
            $(`.page_tab`).eq(nowDisplayPage - 1).find('tr').removeClass('focusNow');
        }
    }

    /**
     * 新增一列在當前focus的元素下方,若無元素被focus則新增一列在最下方
     */
    function plusRow() {
        const $TR = $dataTable[nowDisplayPage - 1].find('tr');
        let isAnyTrAdd = false;
        if ($TR.length !== 0) {
            $TR.each((index, me) => {
                if ($(me).hasClass('focusNow')) {
                    $(me).after(createTableDataElement(nowDisplayPage));
                    isAnyTrAdd = true;
                }
            });
            if (!isAnyTrAdd) {
                $TR.last().after(createTableDataElement(nowDisplayPage));
            }
        } else {
            $dataTable[nowDisplayPage - 1].append(createTableDataElement(nowDisplayPage))
        }
        resetTableRowOrder();
    }

    /**
     * 在最後方新增一頁
     */
    function plusPage() {
        newPage(true);
    }

    /**
     * 在當前頁面後新增一頁
     */
    function plusPageAfterCurrentPage() {
        newPage(true, true);
    }

    /**
     * 刪除當前focus的列元素,若無元素被focus則刪除最下方一列,若只剩一列則刪除當前頁面
     */
    function minusRow() {
        const $TR = $dataTable[nowDisplayPage - 1].find('tr');
        if ($TR.length === 1) {
            minusCurrentPage();
            return;
        }
        let isAnyTrRemove = false;
        $TR.each((index, me) => {
            if ($(me).hasClass('focusNow')) {
                $(me).remove();
                isAnyTrRemove = true;
            }
        });
        if (!isAnyTrRemove) {
            $TR.last().remove();
        }
        resetTableRowOrder();
    }

    /**
     * 刪除最後面一頁,若只剩一頁則不做動作
     */
    function minusPage() {
        if (pageCount <= 1) return;
        const nowPageIndex = nowDisplayPage - 1;
        pageUrl.remove(pageCount - 1);
        $('table:last').remove();
        $('.page_btn:last').remove();
        resetAllPage();
        if (nowPageIndex === pageCount) displayPage(pageCount);
    }

    /**
     * 刪除當前頁面,若只剩一頁則不動作
     */
    function minusCurrentPage() {
        if (pageCount <= 1) return;
        const nowPageIndex = nowDisplayPage - 1;
        pageUrl.remove(nowPageIndex);
        $('table').eq(nowPageIndex).remove();
        $('.page_btn').eq(nowPageIndex).remove();
        resetAllPage();
        displayPage(nowPageIndex === 0 ? 1 : nowPageIndex);
    }

    /**
     * 動畫彈出popup
     * @param popHtml popup的內容
     */
    function popup(popHtml = '') {
        $('.modal-body').html(popHtml);
        $overlay.addClass('state-show');
        $modal.removeClass('state-leave').addClass('state-appear');
    }

    /**
     * 產生popup視窗,給使用者完善test case資料
     */
    function popupTestCaseInformation() {
        let urlElement = '';
        for (let i = 0; i < pageCount; i++) {
            urlElement += `
                    <div   class="pop-block-div">
                        <div><span>page ${i + 1} url : </span></div>
                        <input spellcheck='false'   class="pop-inp pop-url" type='text' />
                    </div>`;
        }
        let popHtml = `
            <div>
                <h3>${projectName}</h3>
                <div class="pop-block-div"   style='text-align: left;font-size: 1.2rem'>
                    <div><span>Test Case Name : </span></div>
                    <input spellcheck='false'   class="pop-inp" id="pop-test-case-name" type='text' />
                </div>
                <div class="popup-inner-div">${urlElement}</div>
                <div class="text-right" id="pop-save"><button>save</button></div>
            </div>`;
        popup(popHtml);
    }

    /**
     * 產生選擇test case的popup是視窗
     * @param testCaseNameArray 所有test case的名稱
     */
    function popupChoseTestCase(testCaseNameArray = []) {
        let testCaseElements = '';
        testCaseNameArray.forEach(name => {
            testCaseElements += `
                <div class="pop-block-div take_json_data"   style='text-align: left;font-size: 1.2rem' case-name='${name}'>
                    <div><span>Test Case Name : </span></div>
                    <div><span>${name} </span></div>
                </div>`;
        });
        let popHtml = `
            <div>
                <h3>${projectName}</h3>
                <div class="popup-inner-div">${testCaseElements}</div>
                <div class="text-right" ><button>load</button></div>
            </div>`;
        popup(popHtml);
    }

    /**
     * 解析json陣列,並設置test case的url
     * @param jsonTestCaseArray test case的json陣列
     * @returns {Promise.<void>}
     */
    async function analyzeJsonArray(jsonTestCaseArray) {
        testCaseName = jsonTestCaseArray[0]['testCaseName'] || '';
        jsonTestCaseArray.forEach(async data => {
            pageUrl.push(data['pageUrl']);
            await injectPageJsonData(JSON.parse(data['dataJsonStr']));
        });
    }

    /**
     * 讀取json data並依照資料內容產生page與table
     * @param pageJsonDataArray json data陣列
     */
    async function injectPageJsonData(pageJsonDataArray = {}) {
        return new Promise((resolve) => {
            newPage();
            let thisDataTable = $(`.page_tab`).eq(pageCount - 1).find('tbody');
            $dataTable.push(thisDataTable);
            pageJsonDataArray.forEach((jsonObj, rowIndex) => {
                thisDataTable.append(createTableDataElement(pageCount));
                const $TD = thisDataTable.find('td');
                const $ITEM = $TD.find('span');
                JSON_OBJECT_KEY.forEach((key, index) => {
                    $ITEM.eq(rowIndex * 8 + index * 2).text(jsonObj[key]);
                    $ITEM.eq(rowIndex * 8 + index * 2 + 1).find('input, select').val(jsonObj[key]);
                });
            });
            resolve('ok');
        });
    }

    /**
     * 創建 table element
     * @returns {string}
     */
    function createTableElement() {
        pageCount++;
        return `
            <table class='table table-responsive table-hover hide page_tab'>
                <thead onselectstart="return false">
                    <tr class='not_focus'>
                        <th>
                            <span class='plus img-hover-opa1 cursor-point'><img class='w1rem' src='./image/plus.png'/></span>
                            <span class='minus  padding-ld6 img-hover-opa1 cursor-point'><img class='w1d4rem' src='./image/minus.png'/></span>
                            <span class='edit  padding-ld6 img-hover-opa1 cursor-point'><img class='w1rem' src='./image/edit.png'/></span>
                            <span class='save padding-ld6 img-hover-opa1 cursor-point'><img class='w1rem' src='./image/save.png'/></span>
                        </th>
                        <th>ID</th>
                        <th>Value</th>
                        <th>Type</th>
                        <th> BeforeScript</th>
                    </tr>
                </thead>
                <tbody class='hide-odd-span'  id='data_table'></tbody>
            </table>`;
    }


    /**
     * 創建table element的內部元素
     * @param page current page 當前頁面
     * @returns {string} new table element string
     */
    function createTableDataElement(page) {
        countDataTableRow[page - 1] = countDataTableRow[page - 1] || 0;
        const ROW = countDataTableRow[page - 1];
        countDataTableRow[page - 1]++;
        return `
            <tr tabindex='1'>
                <td>
                    ${ROW + 1}
                </td>
                <td>
                    <span></span>
                    <span><input spellcheck='false'   type='text'/></span>
                </td>
                <td>
                    <span></span>
                    <span><input spellcheck='false'   type='text'/></span>
                </td>
                <td>
                    <span>text</span>
                    <span>
                        <select>
                            <option value='text'>text</option>
                            <option value='select'>select</option>
                            <option value='radio'>radio</option>
                            <option value='checkbox'>checkbox</option>
                        </select>
                    </span>
                </td>
                <td>
                    <span></span>
                    <span><input spellcheck='false'   type='text'/></span>
                </td>
            </tr>`;
    }

    /**
     *遍歷所有table中的input元素,並將其值保存為物件,若欄位id為空,且before script 也為空,
     * 則保存警告訊息至全域變數generateJsonDataErrorMsg[]
     * @returns {Array} json object array   json 物件
     */
    function generatePageJsonObject(page) {
        let pageJsonObject = [];
        let jsonObject = {};
        const $TR = $dataTable[page - 1].find('tr');
        const $ITEM = $TR.find('td').find('span');
        for (let i = 0; i < $TR.length * 4; i++) {
            jsonObject[JSON_OBJECT_KEY[i % 4]] = $ITEM.eq(i * 2 + 1).find('input, select').val();
            if (i % 4 === 3) {
                pageJsonObject[pageJsonObject.length] = jsonObject;
                if (jsonObject['id'] === '' && jsonObject['beforeScript'] === '') {
                    generateJsonDataErrorMsg.push(`第${page}頁第${~~(i / 4) + 1}列的id不得為空`);
                }
                jsonObject = {};
            }
        }
        return pageJsonObject;
    }

    /**
     * 產生pageData的物件
     * @returns {Array}
     */
    function generateJsonPageDataArray() {
        let jsonPageDataArray = [];
        generateJsonDataErrorMsg = [];
        for (let i = 1; i <= pageCount; i++) {
            let jsonObject = {};
            jsonObject.pageUrl = pageUrl[i - 1];
            jsonObject.jsonDatas = generatePageJsonObject(i);
            jsonObject.testCaseName = testCaseName;
            jsonPageDataArray.push(jsonObject);
        }
        return jsonPageDataArray;
    }

    /**
     * 產生testCase的物件
     * @returns {{testCaseName: string, projectName, pageDatas: Array}}
     */
    function generateJsonTestCase() {
        return {
            testCaseName: testCaseName,
            projectName: projectName,
            pageDatas: generateJsonPageDataArray()
        };
    }

    /**
     * 傳入後端project name回傳json格式的test case
     * @returns {Promise}
     */
    async function getJsonObjectArray() {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "./allTestCaseData?projectName=" + projectName,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (data && data.length !== 0) {
                        resolve(data);
                    } else {
                        reject(new Error('data is empty'));
                    }
                },
                error: function (e) {
                    reject(e);
                }
            });
        });
    }

    /**
     * 將產生的test case 物件傳給後端,預設為PUT傳輸
     * @param mappingType 傳輸的方法
     * @returns {Promise}
     */
    async function sendJsonObjectArray(mappingType = 'PUT') {
        return new Promise((resolve, reject) => {
            let sendData = JSON.stringify(generateJsonTestCase());
            if (generateJsonDataErrorMsg.length !== 0) {
                generateJsonDataErrorMsg.forEach((msg) => {
                    console.log(msg);
                });
                reject(new Error('data is not ok'));
                return;
            }
            $.ajax({
                type: mappingType,
                contentType: "application/json",
                url: "./testCaseData",
                data: JSON.stringify(generateJsonTestCase()),
                timeout: 600000,
                success: function () {
                    resolve('ok');
                }, error: function (e) {
                    reject(e);
                }
            });
        });
    }

    /**
     * 讀取test case物件並依照page data數量產生table
     * @param currentTarget
     * @returns {Promise.<void>}
     */
    async function injectTestCase(currentTarget) {
        deleteAllPage();
        let testCaseArray = await getJsonObjectArrayFromCache();
        let pageDataArray = [];
        testCaseArray.forEach(thisTestCase => {
            if (thisTestCase['testCaseName'] === currentTarget.attr('case-name')) {
                pageDataArray = thisTestCase['pageDatas'];
                testCaseName = thisTestCase['testCaseName'];
            }
        });
        console.time('inject ajax json time');
        await analyzeJsonArray(pageDataArray);
        console.timeEnd('inject ajax json time');
        displayPage(1);
        displayTableSpan(false);
    }

    /**
     * 將取得的 json物件放入 html session緩存
     * @returns {Promise}
     */
    async function cacheJsonObjectData() {
        return new Promise(async (resolve) => {
            let data;
            try {
                data = await getJsonObjectArray();
            } catch (e) {
                console.log(e.message);
            }
            if (Object.prototype.toString.call(data) === '[object Array]') {
                sessionStorage.setItem(projectName + 'JsonArrayData', JSON.stringify(data));
                resolve('ok');
            } else {
                console.log('get data fail');
            }
        });
    }

    /**
     * 從緩存中取得json物件,若緩存為空則呼叫ajax getJsonObjectArray()
     * @returns {Promise.<*>}
     */
    async function getJsonObjectArrayFromCache() {
        let data;
        console.time('take data time');
        if (sessionStorage.getItem(projectName + 'JsonArrayData') !== null) {
            data = JSON.parse(sessionStorage.getItem(projectName + 'JsonArrayData'));
            console.log('json data is get from cache')
        } else {
            try {
                data = await getJsonObjectArray();
            } catch (e) {
                console.log(e);
            }
        }
        console.timeEnd('take data time');
        return data;
    }

    /**
     * 在頁面在入時就將json物件放入緩存
     */
    (async function execCache() {
        await cacheJsonObjectData();
    }());

    /**
     * 檢驗test case資料是否正確
     * @returns {boolean}
     */
    function checkTestCaseData() {
        return !(testCaseName === '' || projectName === '' || pageUrl.length < pageCount);
    }

    /**
     * 輸出test case的細節
     */
    function testCaseDetail() {
        console.log(testCaseName);
        console.log(projectName);
        console.log(pageUrl);
    }

    /**
     * 快捷鍵功能
     */
    const KEY_MAP = (function () {
        return navigator.platform.includes('Mac') ? {
            save: ['ctrlKey', 'shiftKey', 83],
            edit: ['ctrlKey', 'shiftKey', 65],
            plus: ['ctrlKey', 'shiftKey', 90],
            minus: ['ctrlKey', 'shiftKey', 88]
        } : {
            save: ['ctrlKey', 'shiftKey', 83],
            edit: ['ctrlKey', 'shiftKey', 65],
            plus: ['ctrlKey', 'shiftKey', 90],
            minus: ['ctrlKey', 'shiftKey', 88]
        };
    }());

    /**
     * 回傳一個函數能遍歷輸入的快捷鍵功能物件,若有符合的就返回true
     * @param fn
     * @returns {function(*)}
     */
    function forEachBooleanTrue(fn) {
        return (keyArray) => {
            let isMatch = true;
            keyArray.forEach(key => {
                isMatch = isMatch && fn(key);
            });
            return isMatch;
        }
    }

    /**
     * 回傳一個函數能接收key,若key非數字,則檢查event.key是否有值或true,
     * 若為數字則檢查當前按鍵是與key相同
     * @param event
     * @returns {function(*=)}
     */
    function isKeyMatch(event) {
        return (key) => {
            return isNaN(key) ? !!event[key] : (event.which === key);
        };
    }

    //底下為頁面的事件

    /**
     * 新增一個row或page
     */
    $(document).on('click', '.plus', (e) => {
        e.ctrlKey ? plusPage(e) : e.shiftKey ? plusPageAfterCurrentPage(e) : plusRow();
        // !(e.ctrlKey && !(plusPage(e)&&false)) && !(e.shiftKey && !(plusPageAfterCurrentPage(e)&&false)) && !(plusRow(e));
    });

    /**
     * 刪除一個row或page
     */
    $(document).on('click', '.minus', (e) => {
        e.ctrlKey ? minusPage() : e.shiftKey ? minusCurrentPage() : minusRow();
    });

    /**
     * 將table中的input隱藏,p顯示
     */
    $(document).on('click', '.edit', () => {
        displayTableSpan(true);
    });

    $(document).on('click', '.save', async () => {
        testCaseDetail();
        if (!checkTestCaseData()) {
            popupTestCaseInformation();
            $('#pop-test-case-name').val(testCaseName || '');
            let $popUrl = $('.pop-url');
            $popUrl.each((i, e) => {
                e.value = pageUrl[i] || '';
            });
            return;
        }
        displayTableSpan(false);
        let data;
        try {
            data = await sendJsonObjectArray('POST');
        } catch (e) {
            console.log(e.message);
            return;
        }
        if (data === 'ok') {
            sessionStorage.removeItem('jsonArrayData');
            await cacheJsonObjectData();
            console.log('cache is refresh');
        }
    });

    $(document).on('click', '#pop-save', () => {
        testCaseName = $('#pop-test-case-name').val();
        pageUrl = [];
        $('.pop-url').each(function () {
            let $thisVal = $(this).val();
            if ($thisVal && $thisVal !== '') {
                pageUrl.push($thisVal);
            }
        });
        $('.close').trigger('click');
    });

    $(document).on('click', '.my-pagination-link--wide.first', () => {
        $('.page_btn').eq((nowDisplayPage - 1 <= 0) ? pageCount - 1 : nowDisplayPage - 2).trigger('click');
    });

    $(document).on('click', '.my-pagination-link--wide.last', () => {
        $('.page_btn').eq((nowDisplayPage === pageCount) ? 0 : nowDisplayPage).trigger('click');
    });

    $(document).on('click', '#load_test_case_data', async () => {
        let data = await getJsonObjectArrayFromCache();
        if (!data) return;
        let testCaseNameArray = [];
        data.forEach(testCase => {
            testCaseNameArray.push(testCase['testCaseName']);
        });
        popupChoseTestCase(testCaseNameArray);
    });

    //get ajax json data object  array and append in table
    let choseTestCase = '';
    $(document).on({
        click: async function () {
            choseTestCase = $(this);
        },
        dblclick: async function () {
            injectTestCase($(this));
        }
    }, '.take_json_data');

    // generate json array data
    $(document).on('click', '#generate_json_data', () => {
        console.log(JSON.stringify(generateJsonTestCase()));
    });

    //
    $(document).on('click', '#new_page', () => {
        newPage(true);
    });

    $(document).on('click', '.page_btn', (e) => {
        $('.page_btn').removeClass('page-active');
        displayPage(~~$(e.currentTarget).attr('page'));
        // $(e.currentTarget).addClass('page-active');
    });

    $(document).on({
        change: (e) => {
            let $this = $(e.currentTarget);
            $this.parent().parent().find('span').eq(0).text($this.val());
        }
    }, 'input:not(.pop-inp), select');

    $(document).on({
        click: (e) => {
            let $this = $(e.currentTarget);
            clearPageFocusRow();
            if (!$this.hasClass('not_focus')) {
                $this.toggleClass('focusNow');
            }
        }
    }, 'tr');

    $(document).on({
        click: (e) => {
            if ($dataTable[nowDisplayPage - 1] && $dataTable[nowDisplayPage - 1].has(e.target).length === 0) {
                //out side table
                clearPageFocusRow();
            }
        }, keydown: (e) => {
            let keyMach = forEachBooleanTrue(isKeyMatch(e));
            if (keyMach(KEY_MAP.save)) {
                displayTableSpan(false);
            } else if (keyMach(KEY_MAP.edit)) {
                $('.edit')(nowDisplayPage - 1).trigger('click');
            } else if (keyMach(KEY_MAP.plus)) {
                $('.plus').eq(nowDisplayPage - 1).trigger('click');
            } else if (keyMach(KEY_MAP.minus)) {
                $('.minus').eq(nowDisplayPage - 1).trigger('click');
            }
        }, keyup: (e) => {

        }
    }, 'html');

    //page event end

    // Array Remove - By John Resig (MIT Licensed)
    Array.prototype.remove = function (from, to) {
        let rest = this.slice((to || from) + 1 || this.length);
        this.length = from < 0 ? this.length + from : from;
        return this.push.apply(this, rest);
    };
}(jQuery));

