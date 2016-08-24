var Item = React.createClass({
    render: function () {
        return (
            <div>
                <h2>{ this.props.name }</h2>
                <p>{ this.props.content } </p>
            </div>
        )
    }
});

var Items = React.createClass({
    render: function () {
        var itemNodes = this.props.items.map(function ( item ) {
            return <Item name={ item.name } content={ item.content } />
        });

        return (
            <div className="item-list">
                { itemNodes }
            </div>
        )
    }
});

React.createClass({
    render: function() {
        return (
            <html><head><title>Some Items we have here</title></head>
            <body><p>
                <Items items={this.props.items}/>
            </p></body>
            </html>
        )
    }
});