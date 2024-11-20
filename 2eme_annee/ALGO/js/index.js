//const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest

const fetch = require("fetch")

/*
function fetch(url, onSuccess, onFail) {

    // Async only if a callback is defined: 
    var async = onSuccess ? true : false;
    // (Don't complain about the inefficiency
    //  of this line; you're missing the point.)

    var req; // XMLHttpRequest object.

    // The XMLHttpRequest callback:
    function processReqChange() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                if (onSuccess)
                    onSuccess(req.responseText, url, req);
            } else {
                if (onFail)
                    onFail(url, req);
            }
        }
    }

    // Create the XMLHttpRequest object:
    req = new XMLHttpRequest();

    // If asynchronous, set the callback:
    if (async)
        req.onreadystatechange = processReqChange;

    // Fire off the request:
    req.open("GET", url, async);
    req.send(null);

    // If asynchronous,
    //  return request object; or else
    //  return the response.
    if (async)
        return req;
    else
        return req.responseText;
}
*/
