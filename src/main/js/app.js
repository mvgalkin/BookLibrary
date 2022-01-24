'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');


class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {books: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/api/best_books'}).done(response => {
            this.setState({books: response.entity});
        });
    }

    render() {
        return (
            <BookList books={this.state.books}/>
        )
    }
}


class BookList extends React.Component{
    render() {
        const books = this.props.books.map(book =>
            <Book key={book.name} books={book}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Название</th>
                    <th>Обложка</th>
                    <th>Авторы</th>
                    <th>Жанр</th>
                </tr>
                {books}
                </tbody>
            </table>
        )
    }
}

class Book extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.books.name}</td>
                <td>{this.props.books.cover}</td>
                <td>{this.props.books.authors.map(a => a.name).join(', ')}</td>
                <td>{this.props.books.genres.map(g => g.name).join(', ')}</td>
            </tr>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)
