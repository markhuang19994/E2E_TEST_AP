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

    //is key press flag


    initPage();

    function initPage() {
        newPage(true);
        displayPage(pageCount);
    }

    //page is begin at 1,2,3....
    function newPage(initFirst = false) {
        $appendTable.append(createTableElement(++pageCount));
        $dataTable[pageCount - 1] = $(`#page_${pageCount}`).find('#data_table');
        if (initFirst) {
            $dataTable[pageCount - 1].append(createTableDataElement(pageCount));
        }
        $('#append_page_number').append(`<button class="page_btn" page="${pageCount}">${pageCount}</button>`);
    }

    function displayPage(page) {
        $(`table`).addClass('hide');
        $(`#page_${page}`).removeClass('hide');
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

    function resetTableRowOrder() {
        countDataTableRow[nowDisplayPage - 1] = 0;
        $dataTable[nowDisplayPage - 1].find('tr').each((index, me) => {
            $(me).find('td:first').text(index + 1);
            countDataTableRow[nowDisplayPage - 1] = index + 1;
        });
    }

    function displayTableSpan(isWithInput) {
        if (isWithInput) {
            $dataTable[nowDisplayPage - 1].addClass('hide-odd-span');
            $dataTable[nowDisplayPage - 1].removeClass('hide-even-span');
        } else {
            $dataTable[nowDisplayPage - 1].addClass('hide-even-span');
            $dataTable[nowDisplayPage - 1].removeClass('hide-odd-span');
        }
    }

    function clearPageFocusRow() {
        if ((!window.event.ctrlKey && !window.event.shiftKey)) {
            $(`#page_${nowDisplayPage}`).find('tr').removeClass('focusNow');
        }
    }


    /**
     * Inject json data in table
     * @param pageJsonDataArray json data array
     */
    function injectPageJsonData(pageJsonDataArray = {}) {
        newPage();
        let thisDataTable = $(`#page_${pageCount}`).find('#data_table');
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
        return new Promise((resolve, reject) => {
            resolve('ok');
        });
    }

    function analyzeJsonArray(jsonDataArray) {
        jsonDataArray.forEach(data => injectPageJsonData(JSON.parse(data['dataJsonStr'])));
    }

    function createTableElement(page) {
        return `
            <table class='table table-hover hide' id='page_${page}'>
                <thead onselectstart="return false">
                    <tr class='not_focus'>
                        <th style="width: 12%">
                            <span class='plus'><img class='w1rem' src='./image/plus.png'/></span>
                            <span class='minus'><img class='w1d4rem margin-l1-r1' src='./image/minus.png'/></span>
                            <span class='edit'><img class='w1rem' src='./image/edit.png'/></span>
                        </th>
                        <th style="width: 22%">ID</th>
                        <th style="width: 22%">Value</th>
                        <th style="width: 22%">Type</th>
                        <th style="width: 22%"> BeforeScript</th>
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
                    <span><input type='text' name='tab_${row}_0' id='tab_${row}_0'/></span>
                </td>
                <td>
                    <span></span>
                    <span><input type='text' name='tab_${row}_1' id='tab_${row}_1'/></span>
                </td>
                <td>
                    <span></span>
                    <span>
                        <select id='tab_${row}_2'>
                            <option value='text'>text</option>
                            <option value='select'>select</option>
                            <option value='radio'>radio</option>
                            <option value='checkbox'>checkbox</option>
                        </select>
                    </span>
                </td>
                <td>
                    <span></span>
                    <span><input type='text' name='tab_${row}_3' id='tab_${row}_3'/></span>
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
            jsonObject.url = 'www.google.com';
            jsonObject.dataJsonStr = generatePageJsonObject(i);
            jsonObjectArray.push(jsonObject);
        }
        return jsonObjectArray;
    }


    function getJsonObjectArray() {
        return $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "./json_test_data",
            data: {name: 'Mark'},
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                return new Promise((resolve, reject) => {
                    if (data && data.length !== 0) {
                        resolve(data);
                    } else {
                        reject('data is empty');
                    }
                });
            },
            error: function (e) {
                console.log(`error : ${e}`);
                return new Promise((resolve, reject) => {
                    reject('get data error');
                });
            }
        });
    }

    //page event write under here
    //create new table element string and append in table
    $(document).on('click', '.plus', () => {
        $dataTable[nowDisplayPage - 1].append(createTableDataElement(nowDisplayPage))
    });

    $(document).on('click', '.minus', () => {
        let $tr = $dataTable[nowDisplayPage - 1].find('tr');
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
    });

    $(document).on('click', '.edit', () => {
        displayTableSpan(true);
    });

    //get ajax json data object  array and append in table
    $(document).on('click', '#take_json_data', (e) => {
        deleteAllPage();
        getJsonObjectArray().then((data) => {
            console.time('inject ajax json time');
            analyzeJsonArray(data);
            console.timeEnd('inject ajax json time');
        }).then(() => {
            /*need to wait create and append element until success or set time out interval
            * if not Dom element may not read from jQuery dom selector*/
            displayPage(1)
        });
    });

    //generate json array data
    $(document).on('click', '#generate_json_data', (e) => {
        alert(JSON.stringify(generateJsonObjectArray()));
    });

    //
    $(document).on('click', '#new_page', (e) => {
        newPage(true);
        displayPage(pageCount);
    });

    $(document).on('click', '.page_btn', (e) => {
        displayPage($(e.currentTarget).attr('page'));
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
        // ,
        // dblclick: (e) => {
        //     let $thisTd = $(e.currentTarget).find('td');
        //     $thisTd.find('span').each((index, me) => {
        //         if (index % 2 === 0) {
        //             $(me).addClass('hide');
        //         } else {
        //             $(me).removeClass('hide');
        //         }
        //     });
        // }
    }, 'tr');

    $(document).on({
        keydown: (e) => {
            if (e.which === 83 && e.ctrlKey && e.shiftKey) {
                displayTableSpan(false);
            }
        },
        keyup: (e) => {

        }
    }, 'html');

    //page event end
}(jQuery));

