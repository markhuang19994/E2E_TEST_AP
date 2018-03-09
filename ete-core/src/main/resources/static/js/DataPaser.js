(function ($) {
    let arr = [
        {
            id: 'my first id',
            type: 'radio',
            val: 'my first value',
            beforeScript: 'console.log("Yoooo")'
        }, {
            id: 5,
            type: 6,
            val: 7,
            beforeScript: 8
        }
    ]

    arr.forEach(jsonObj => {
        Object.keys(jsonObj).forEach(key => {
            if (key === 'beforeScript')
                new Function(jsonObj[key])()
            else
                console.log('key = ' + key + ', val = ' + jsonObj[key])
        });
    });
}(jQuery));

