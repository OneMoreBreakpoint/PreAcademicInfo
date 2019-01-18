$(document).ready(function () {
    $(".modal-close").click(() => {
        displayModal('.modal-box', false);
    });
    $(".modal-window").click((event) => {
        event.stopPropagation();
    });
});

/**
 * Displays or hides a modal window
 * @param {string} cssSelector - css selector that uniquely identifies the modal
 * @param {boolean} display - true to display modal, false to hide it
 */
function displayModal(cssSelector, display) {
    let displayMode = (display ? 'flex' : 'none');
    $(cssSelector).css("display", displayMode);
}

/**
 * Appends a set of query params to an existing url or replaces them if they already exist
 * @param url {string} - the url to append to
 * @param queryParams {object} - a set of key-value pairs
 * @returns {string}
 */
function updateQueryParams(url, queryParams) {
    let indexOfQueryStart = url.indexOf('?'), queryObject = {};
    if (indexOfQueryStart !== -1) {
        let crtQueryString = url.substring(indexOfQueryStart + 1);
        queryObject = buildQueryObject(crtQueryString);
    }
    for (let key in queryParams) {
        queryObject[key] = queryParams[key];
    }
    return url.split('?')[0] + '?' + $.param(queryObject);
}

function buildQueryObject(queryString) {
    let crtQueryObject = {};
    let keyValuePairs = queryString.split('&');
    for (let i = 0; i < keyValuePairs.length; i++) {
        let key = keyValuePairs[i].split('=')[0];
        let value = keyValuePairs[i].split('=')[1];
        crtQueryObject[key] = value;
    }
    return crtQueryObject;
}

/**
 * @param domId - id with pattern "<nameOfDomElement>-<numericId>" (ex: "div-32")
 * @returns {number} - <numericId>
 */
function getNumericIdFromDomId(domId) {
    let hyphenPos = domId.indexOf('-');
    return domId.substr(hyphenPos + 1);
}

/**
 * @param jsObj {object}
 * @returns {object}
 */
function deepClone(jsObj) {
    return JSON.parse(JSON.stringify(jsObj));
}

/**
 * returns a valid number within given range based on actual value.
 * If actual value is less than minValue, then minValue is returned.
 * If actual value is greater than maxValue, then maxValue is returned
 * If actual value is between minValue and maxValue, then actual value is returned.
 * If actual value is not a valid number, then undefined is returned.
 * @param {boolean} acceptFloat - specifies if valid number can be a floating point number; default is false
 * @returns {number | undefined}
 */
function getValidNumber(actualValue, minValue, maxValue, acceptFloat = false) {
    if (typeof(actualValue) === "string") {
        actualValue = parseInt(actualValue);
    }
    if (isNaN(actualValue)) {
        return undefined;
    }
    if (!acceptFloat) {
        actualValue = Math.round(actualValue);
    }
    if (actualValue > maxValue) {
        return maxValue;
    } else if (actualValue < minValue) {
        return minValue;
    }
    return actualValue;
}