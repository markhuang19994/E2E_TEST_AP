/**
 * @author MarkHuang
 * @since  2018/3/08
 */
(function ($) {
    //所有table element 最外層的div
    const $appendTable = $('#append_table');
    const jsonObjectKey = ['id', 'value', 'dataType', 'beforeScript'];
    let testCaseName = '';
    let pageUrl = [];
    //紀錄每一頁的table element
    let $dataTable = [];
    //紀錄page中table的行數
    let countDataTableRow = [];
    //有幾頁,所謂的page這邊不是真的分頁,只是複數table的隱藏與顯示,且一個table對應一個page,一次只顯示一個table
    let pageCount = 0;
    let nowDisplayPage = 0;

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
            $appendTable.append(createTableElement());
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
        let $table = $appendTable.find('table');
        let $pageNumber = $('.page_btn');
        $appendTable.find('tbody').each((index, me) => {
            pageCount++;
            $table.eq(index).attr('id', 'page_' + (index + 1));
            countDataTableRow.push($(me).find('tr').length);
            $dataTable.push($(me));
            $pageNumber.eq(index).attr('page', pageCount);
            $pageNumber.eq(index).find('.my-pagination-link').text(index + 1);
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
     * @param isWithInput 是否顯示input元素
     */
    function displayTableSpan(isWithInput) {
        const tbody = $('tbody');
        if (isWithInput) {
            tbody.addClass('hide-odd-span');
            tbody.removeClass('hide-even-span');
        } else {
            tbody.addClass('hide-even-span');
            tbody.removeClass('hide-odd-span');
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
        let $tr = $dataTable[nowDisplayPage - 1].find('tr');
        let isAnyTrAdd = false;
        if ($tr.length !== 0) {
            $tr.each((index, me) => {
                if ($(me).hasClass('focusNow')) {
                    $(me).after(createTableDataElement(nowDisplayPage));
                    isAnyTrAdd = true;
                }
            });
            if (!isAnyTrAdd) {
                $tr.last().after(createTableDataElement(nowDisplayPage));
            }
        } else {
            $dataTable[nowDisplayPage - 1].append(createTableDataElement(nowDisplayPage))
        }
        resetTableRowOrder();
    }
    
    function plusPage() {
        newPage(true);
    }
    
    function plusPageAfterCurrentPage() {
        newPage(true, true);
    }

    /**
     * 刪除當前focus的列元素,若無元素被focus則刪除最下方一列,若只剩一列則刪除當前頁面
     */
    function minusRow() {
        let $tr = $dataTable[nowDisplayPage - 1].find('tr');
        if ($tr.length === 1) {
            minusCurrentPage();
            return;
        }
        let isAnyTrRemove = false;
        $tr.each((index, me) => {
            if ($(me).hasClass('focusNow')) {
                $(me).remove();
                isAnyTrRemove = true;
            }
        });
        if (!isAnyTrRemove) {
            $tr.last().remove();
        }
        resetTableRowOrder();
    }

    /**
     * 刪除最後面一頁,若只剩一頁則不做動作
     */
    function minusPage() {
        if (pageCount <= 1) return;
        let nowPageIndex = nowDisplayPage - 1;
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
        let nowPageIndex = nowDisplayPage - 1;
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
                        <input class="pop-inp pop-url" type='text' />
                    </div>`;
        }
        let popHtml = `
            <div>
                <h3>${projectName}</h3>
                <div class="pop-block-div"   style='text-align: left;font-size: 1.2rem'>
                    <div><span>Test Case Name : </span></div>
                    <input class="pop-inp" id="pop-test-case-name" type='text' />
                </div>
                <div class="popup-inner-div">${urlElement}</div>
                <div class="text-right" ><button>save</button></div>
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
        return new Promise((resolve, reject) => {
            newPage();
            let thisDataTable = $(`.page_tab`).eq(pageCount - 1).find('tbody');
            $dataTable.push(thisDataTable);
            pageJsonDataArray.forEach((jsonObj, rowIndex) => {
                thisDataTable.append(createTableDataElement(pageCount));
                let $td = thisDataTable.find('td');
                let $item = $td.find('span');
                jsonObjectKey.forEach((key, index) => {
                    $item.eq(rowIndex * 8 + index * 2).text(jsonObj[key]);
                    $item.eq(rowIndex * 8 + index * 2 + 1).find('input, select').val(jsonObj[key]);
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
                <tbody class='hide-odd-span'  id='data_table'><!-- input in table id: tab_row_column --></tbody>
            </table>`;
    }


    /**
     * 創建table element的內部元素
     * @param page current page 當前頁面
     * @returns {string} new table element string
     */
    function createTableDataElement(page) {
        countDataTableRow[page - 1] = countDataTableRow[page - 1] || 0;
        let row = countDataTableRow[page - 1];
        countDataTableRow[page - 1]++;
        return `
            <tr tabindex='1'>
                <td>
                    ${row + 1}
                </td>
                <td>
                    <span></span>
                    <span><input type='text'/></span>
                </td>
                <td>
                    <span></span>
                    <span><input type='text'/></span>
                </td>
                <td>
                    <span></span>
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
                    <span><input type='text'/></span>
                </td>
            </tr>`;
    }

    /**
     * Iterate all elements in table and set in json object array
     * @returns {Array} json object array
     */
    function generatePageJsonObject(page) {
        let pageJsonObject = [];
        let jsonObject = {};
        let $tr = $dataTable[page - 1].find('tr');
        let $item = $tr.find('td').find('span');
        for (let i = 0; i < $tr.length * 4; i++) {
            jsonObject[jsonObjectKey[i % 4]] = $item.eq(i * 2 + 1).find('input, select').val();
            if (i % 4 === 3) {
                pageJsonObject[pageJsonObject.length] = jsonObject;
                jsonObject = {};
            }
        }
        return pageJsonObject;
    }

    function generateJsonPageDataArray() {
        let jsonPageDataArray = [];
        for (let i = 1; i <= pageCount; i++) {
            let jsonObject = {};
            jsonObject.pageUrl = pageUrl[i - 1];
            jsonObject.jsonDatas = generatePageJsonObject(i);
            jsonObject.testCaseName = testCaseName;
            jsonPageDataArray.push(jsonObject);
        }
        return jsonPageDataArray;
    }

    function generateJsonTestCase() {
        return {
            testCaseName: testCaseName,
            projectName: projectName,
            pageDatas: generateJsonPageDataArray()
        };
    }

    async function getJsonObjectArray() {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "./allTestCaseData?projectName=" + projectName,
                data: {name: 'Mark'},
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (data && data.length !== 0) {
                        resolve(data);
                    } else {
                        reject('data is empty');
                    }
                },
                error: function (e) {
                    console.log(`error : ${e}`);
                    reject('get data error');
                }
            });
        });
    }

    async function sendJsonObjectArray() {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "./testCaseData",
                data: JSON.stringify(generateJsonTestCase()),
                timeout: 600000,
                success: function () {
                    resolve('ok');
                }, error: function () {
                    reject('send json object array fail');
                }
            });
        });
    }

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

    async function cacheJsonObjectData() {
        return new Promise(async (resolve, reject) => {
            const data = await getJsonObjectArray();
            if (Object.prototype.toString.call(data) === '[object Array]') {
                sessionStorage.setItem('jsonArrayData', JSON.stringify(data));
                resolve('ok');
            } else {
                reject('get data fail');
            }
        });
    }

    async function getJsonObjectArrayFromCache() {
        let data;
        console.time('take data time');
        if (sessionStorage.getItem('jsonArrayData') !== null) {
            data = JSON.parse(sessionStorage.getItem('jsonArrayData'));
            console.log('json data is get from cache')
        } else {
            data = await getJsonObjectArray();
        }
        console.timeEnd('take data time');
        return data;
    }

    (async function execCache() {
        await cacheJsonObjectData();
    }());


    const keyMap = (function () {
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

    function forEachBooleanTrue(fn) {
        return (keyArray) => {
            let isMatch = true;
            keyArray.forEach(key => {
                isMatch = isMatch && fn(key);
            });
            return isMatch;
        }
    }

    function isKeyMatch(event) {
        return (key) => {
            return isNaN(key) ? !!event[key] : (event.which === key);
        };
    }

    //page event write under here
    //create new table element string and append in table
    $(document).on('click', '.plus', (e) => {
        e.ctrlKey ? plusPage(e) : e.shiftKey ? plusPageAfterCurrentPage(e) : plusRow();
        // !(e.ctrlKey && !(plusPage(e)&&false)) && !(e.shiftKey && !(plusPageAfterCurrentPage(e)&&false)) && !(plusRow(e));
    });

    $(document).on('click', '.minus', (e) => {
        e.ctrlKey ? minusPage() : e.shiftKey ? minusCurrentPage() : minusRow();
    });

    $(document).on('click', '.edit', () => {
        displayTableSpan(true);
    });

    function checkTestCaseData() {
        return !(testCaseName === '' || projectName === '' || pageUrl.length < pageCount);
    }

    function testCaseDetail() {
        console.log(testCaseName);
        console.log(projectName);
        console.log(pageUrl);
    }

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
        let data = await sendJsonObjectArray();
        if (data === 'ok') {
            sessionStorage.removeItem('jsonArrayData');
            await cacheJsonObjectData();
            console.log('cache is refresh');
        }
    });

    $(document).on('click', '.my-pagination-link--wide.first', () => {
        $('.page_btn').eq((nowDisplayPage - 1 <= 0) ? pageCount - 1 : nowDisplayPage - 2).trigger('click');
    });

    $(document).on('click', '.my-pagination-link--wide.last', () => {
        $('.page_btn').eq((nowDisplayPage === pageCount) ? 0 : nowDisplayPage).trigger('click');
    });

    $(document).on('click', '#load_test_case_data', async () => {
        let data = await getJsonObjectArrayFromCache();
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
        }, dblclick: async function () {
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
            if (keyMach(keyMap.save)) {
                displayTableSpan(false);
            } else if (keyMach(keyMap.edit)) {
                $('.edit')(nowDisplayPage - 1).trigger('click');
            } else if (keyMach(keyMap.plus)) {
                $('.plus').eq(nowDisplayPage - 1).trigger('click');
            } else if (keyMach(keyMap.minus)) {
                $('.minus').eq(nowDisplayPage - 1).trigger('click');
            }
        }, keyup: (e) => {

        }
    }, 'html');

    //page event end
}(jQuery));

