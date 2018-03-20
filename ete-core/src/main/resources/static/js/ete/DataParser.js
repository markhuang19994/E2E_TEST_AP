/**
 * @author MarkHuang
 * @since  2018/3/08
 */
(function ($) {
    const $appendTable = $('#append_table');
    const jsonObjectKey = ['id', 'value', 'dataType', 'beforeScript'];
    //紀錄page的data table 物件
    let $dataTable = [];
    //紀錄page中table的行數
    let countDataTableRow = [];
    let pageCount = 0;
    let nowDisplayPage = 0;

    (function initPage() {
        newPage(true);
        displayPage(pageCount);
        $('.page_btn').eq(nowDisplayPage - 1).addClass('page-active');
    }());

    //page is begin at 1,2,3....
    function newPage(initFirst = false, isAfter = false) {
        if (isAfter) {
            $dataTable[nowDisplayPage - 1].parent().after(createTableElement(++pageCount));
        } else {
            $appendTable.append(createTableElement(++pageCount));
        }
        $dataTable[pageCount - 1] = $(`.page_tab`).eq(pageCount - 1).find('tbody');
        if (initFirst) {
            $dataTable[pageCount - 1].append(createTableDataElement(pageCount));
        }
        $('#append_page_number').before(
            `<li class="my-pagination-item page_btn cursor-point" page="${pageCount}">
                <span class=" my-pagination-link ">
                    ${pageCount}
                </span>
            </li>`);
    }

    function displayPage(page) {
        $(`table`).addClass('hide');
        $(`.page_tab`).eq(page - 1).removeClass('hide');
        clearAllPageAction();
        $('.page_btn').eq(page - 1).addClass('page-active');
        nowDisplayPage = page;
    }

    function deleteAllPage() {
        $('table').remove();
        $('.page_btn').remove();
        $dataTable = [];
        countDataTableRow = [];
        pageCount = 0;
        nowDisplayPage = 0;
    }

    function clearAllPageAction() {
        $('.page_btn').removeClass('page-active');
    }

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

    function resetTableRowOrder() {
        countDataTableRow[nowDisplayPage - 1] = 0;
        $dataTable[nowDisplayPage - 1].find('tr').each((index, me) => {
            $(me).find('td:first').text(index + 1);
            countDataTableRow[nowDisplayPage - 1] = index + 1;
        });
    }

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

    function clearPageFocusRow() {
        if ((!window.event.ctrlKey && !window.event.shiftKey)) {
            $(`.page_tab`).eq(nowDisplayPage - 1).find('tr').removeClass('focusNow');
        }
    }

    function plusRow(e) {
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
        resetTableRowOrder(e);
    }

    function plusPage() {
        newPage(true);
    }

    function plusPageAfterCurrentPage() {
        newPage(true, true);
        resetAllPage();
    }

    function minusRow(e) {
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
        resetTableRowOrder(e);
    }

    function minusPage() {
        if (pageCount <= 1) return;
        let nowPageIndex = nowDisplayPage - 1;
        $('table:last').remove();
        $('.page_btn:last').remove();
        resetAllPage();
        if (nowPageIndex === pageCount) displayPage(pageCount);
    }

    function minusCurrentPage() {
        if (pageCount <= 1) return;
        let nowPageIndex = nowDisplayPage - 1;
        $('table').eq(nowPageIndex).remove();
        $('.page_btn').eq(nowPageIndex).remove();
        resetAllPage();
        displayPage(nowPageIndex === 0 ? 1 : nowPageIndex);
    }


    /**
     * Inject json data in table
     * @param pageJsonDataArray json data array
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
                    /*This is old solution to inject data in table is 200%~550% slower then new solution
                    console.log(`$(#tab_${countDataTableRow[pageCount - 1] - 1}_${index % 4}).val(${jsonObj[key]})`);
                    thisDataTable.find(`#tab_${countDataTableRow[pageCount - 1] - 1}_${index % 4}`).val(jsonObj[key]);
                   thisDataTable.find(`#tab_sp_${countDataTableRow[pageCount - 1] - 1}_${index % 4}`).text(jsonObj[key]);*/
                    $item.eq(rowIndex * 8 + index * 2).text(jsonObj[key]);
                    $item.eq(rowIndex * 8 + index * 2 + 1).find('input, select').val(jsonObj[key]);
                });
            });
            resolve('ok');
        });
    }

    function analyzeJsonArray(jsonDataArray) {
        jsonDataArray.forEach(data => injectPageJsonData(JSON.parse(data['dataJsonStr'])));
    }

    function createTableElement(page) {
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
     * Create table data element  and set select and input name and id by row
     * @param page current page
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

    function generateJsonObjectArray() {
        let jsonObjectArray = [];
        for (let i = 1; i <= pageCount; i++) {
            let jsonObject = {};
            jsonObject.pageUrl = 'www.google.com';
            jsonObject.jsonDatas = generatePageJsonObject(i);
            jsonObjectArray.push(jsonObject);
        }
        return jsonObjectArray;
    }

    async function getJsonObjectArray() {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "./json_test_data",
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
                type: "POST",
                contentType: "application/json",
                url: "./json_test_data",
                data: JSON.stringify(generateJsonObjectArray()),
                timeout: 600000,
                success: function () {
                    resolve('ok');
                }, error: function () {
                    reject('send json object array fail');
                }
            });
        });
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
        e.ctrlKey ? plusPage(e) : e.shiftKey ? plusPageAfterCurrentPage(e) : plusRow(e);
        // !(e.ctrlKey && !(plusPage(e)&&false)) && !(e.shiftKey && !(plusPageAfterCurrentPage(e)&&false)) && !(plusRow(e));
    });

    $(document).on('click', '.minus', (e) => {
        e.ctrlKey ? minusPage() : e.shiftKey ? minusCurrentPage() : minusRow(e);
    });

    $(document).on('click', '.edit', () => {
        displayTableSpan(true);
    });

    $(document).on('click', '.save', async () => {
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

    //get ajax json data object  array and append in table
    $(document).on('click', '#take_json_data', async () => {
        deleteAllPage();
        let data;
        console.time('take data time');
        if (sessionStorage.getItem('jsonArrayData') !== null) {
            data = JSON.parse(sessionStorage.getItem('jsonArrayData'));
            console.log('json data is get from cache')
        } else {
            data = await getJsonObjectArray();
        }
        console.timeEnd('take data time');
        console.time('inject ajax json time');
        await analyzeJsonArray(data);
        console.timeEnd('inject ajax json time');
        displayPage(1);
        displayTableSpan(false);
    });

    //generate json array data
    $(document).on('click', '#generate_json_data', () => {
        alert(JSON.stringify(generateJsonObjectArray()));
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
    }, 'input, select');

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

