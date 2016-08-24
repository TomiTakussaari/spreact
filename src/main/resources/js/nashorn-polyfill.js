// These are required by reactJs and should be provided by environment
global = {};
console = {};
console.debug = print;
console.warn = print;
console.log = print;
console.error = print;

function renderWithJSObjectModel(template, model) {
    var data = {};
    for (var k in model) {
        data[k] = JSON.parse(model[k])
    }
    var element = React.createElement(eval(template), data);
    return ReactDOMServer.renderToStaticMarkup(element);
}

function renderJsx(template, model) {
    var jsTemplate = Babel.transform(template, {presets: ['react']}).code;
    return renderWithJSObjectModel(jsTemplate, model);
}
