/**
 * @author MarkHuang
 * @since  2018/4/12
 */
(function ($) {
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

    //沒有使用extends是為了方便以後改成export {...}
    window.ShortKey = ShortKey;
    window.PopUp = PopUp;
}(jQuery));