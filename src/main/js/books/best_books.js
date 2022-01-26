const React = require("react");

export class BestBooks extends React.Component{
    render() {
        console.warn("BEST")
        console.warn(this.props.books)
        return (
            <div>
                <h2>Лучшие книги представленные в нашей билиотеке:</h2>
                <BookInfoList books={this.props.books}/>
            </div>
        )
    }
}

class BookInfoList extends React.Component{
    render() {
        var books = [];
        if (this.props.books.length>0) {
            books = this.props.books.map(book =>
                <BookInfo key={book.name} books={book}/>
            );
        } 

        return (
            <table className="books">
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

class BookInfo extends React.Component{
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