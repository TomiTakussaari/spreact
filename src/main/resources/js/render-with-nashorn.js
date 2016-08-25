// polyfill, required by reactJs and others
global = {};
console = {};
console.debug = print;
console.warn = print;
console.log = print;
console.error = print;

function renderWithWithJSONModel(template, model) {
    var data = {};
    for (var k in model) {
        data[k] = JSON.parse(model[k])
    }
    var element = React.createElement(eval(template), data);
    return ReactDOMServer.renderToStaticMarkup(element);
}

function renderJsx(template) {
    return Babel.transform(template, {presets: ['react']}).code;
}
