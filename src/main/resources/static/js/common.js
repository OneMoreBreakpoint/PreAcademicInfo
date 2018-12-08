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
    return parseInt(domId.split('-')[1]);
}

/**
 * @param jsObj {object}
 * @returns {object}
 */
function deepClone(jsObj) {
    return JSON.parse(JSON.stringify(jsObj));
}