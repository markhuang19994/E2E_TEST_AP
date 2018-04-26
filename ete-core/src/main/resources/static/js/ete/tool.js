/**
 * @author MarkHuang
 * @since  2018/4/12
 */
(($) => {
    /**
     * 快捷鍵觸發功能
     */
    class ShortKey {

        static init(keyMap, keyFunction) {
            $(document).on({
                keydown: (e) => {
                    const KEY_MACH = ShortKey.forEachBooleanTrue(ShortKey.isKeyMatch(e));
                    Object.keys(keyMap).forEach((key) => (KEY_MACH(keyMap[key])) && keyFunction[key]());
                }
            }, 'html');
        };

        /**
         * 回傳一個函數能遍歷輸入的快捷鍵功能物件,若有符合的就返回true
         * @param fn
         * @returns {function(*)}
         */
        static forEachBooleanTrue(fn) {
            return (keyArray) => {
                let isMatch = true;
                keyArray.forEach(key => isMatch = isMatch && fn(key));
                return isMatch;
            }
        };

        /**
         * 回傳一個函數能接收key,若key非數字,則檢查event.key是否有值或true,
         * 若為數字則檢查當前按鍵是與key相同
         * @param event
         * @returns {function(*=)}
         */
        static isKeyMatch(event) {
            return (key) => isNaN(key) ? !!event[key] : (event.which === key);
        }
    }

    /**
     * 彈出視窗功能
     */
    class PopUp {
        static init() {
            this.isInit = true;
            this.modalOverlaydalFrame = $('.modal-frame');
            this.modalOverlay = $('.modal-overlay');

            //由於目前class不支援fat arrow function(箭頭函數),
            this.modalOverlaydalFrame.bind('webkitAnimationEnd oanimationend msAnimationEnd animationend', function (e) {
                if (this.modalOverlaydalFrame.hasClass('state-leave')) {
                    this.modalOverlaydalFrame.removeClass('state-leave');
                }
            }.bind(this));

            $('.close').on('click', function () {
                this.modalOverlay.removeClass('state-show');
                this.modalOverlaydalFrame.removeClass('state-appear').addClass('state-leave');
            }.bind(this));

            $('.open').on('click', function () {
                this.modalOverlay.addClass('state-show');
                this.modalOverlaydalFrame.removeClass('state-leave').addClass('state-appear');
            }.bind(this));
        }

        /**
         * 動畫彈出popup
         * @param popHtml popup的內容
         */
        static pop(popHtml = '') {
            if (!this.isInit) this.init();
            $('.modal-body').html(popHtml);
            this.modalOverlay.addClass('state-show');
            this.modalOverlaydalFrame.removeClass('state-leave').addClass('state-appear');
        }
    }

    /**
     * 區分顏色的console,如果沒有傳遞顏色就會顯示平常的log
     * 如果傳遞的不是訊息也會顯示平常的log
     */
    class Logger {

        static debug(message) {
            this.colorLog(message, "black");
        }

        static info(message) {
            this.colorLog(message, "DodgerBlue");
        }

        static success(message) {
            this.colorLog(message, "Green");
        }

        static error(message) {
            this.colorLog(message, "Red")
        }

        static warn(message) {
            this.colorLog(message, "Orange")
        }

        static colorLog(message, color = "black") {
            if (Object.prototype.toString.call(message) === '[object String]') {
                console.log(`%c${message}`, `color:${color}`);
            } else {
                console.log(message);
            }
        }
    }

    class EteArrayUtil {
        /**
         * 回傳一個新數組,並在指定位置插入值
         * @param arr 原數組
         * @param index 插入索引
         * @param insertItem 插入值
         */
        static insert(arr, index, insertItem) {
            return [...arr.slice(0, index), insertItem, ...arr.slice(index)];
        }

        /**
         *回傳一個刪除原數組指定位置的新數組
         * @param arr 原數組
         * @param from  刪除的起始點
         * @param to 刪除的結束點
         * @returns {Array} 新數組
         */
        static remove(arr, from, to) {
            let newArr = [...arr];
            let rest = newArr.slice((to || from) + 1 || newArr.length);
            newArr.length = from < 0 ? newArr.length + from : from;
            newArr.push.apply(newArr, rest);
            return newArr;
        };

        static trim(arr) {
            return arr.filter((val) => val && val !== '');
        }
    }

    //沒有使用extends是為了方便以後改成export {...}
    window.ShortKey = ShortKey;
    window.PopUp = PopUp;
    window.Logger = Logger;
    window.EteArrayUtil = EteArrayUtil;
})($);