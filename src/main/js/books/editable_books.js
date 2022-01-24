const React = require("react");

export class EditableBooksList extends React.Component{
    render() {
        var pageNumber = this.props.paged_books?.pageable?.pageNumber
        if (pageNumber == null) {
            pageNumber = 0
        }
        var totalPages = this.props.paged_books?.totalPages
        if (totalPages == null) {
            totalPages = 1
        }
        const content = this.props.paged_books.content
        console.warn(content)
        return (
            <div>
                <h2>Наша библиотека:</h2>
                <form>
                    <div align="right">
                        <PagingInfo pageNumber={pageNumber}
                                    totalPages={totalPages}/>
                    </div>
                    <div align="left">
                        <input type="button" value="Добавить"/>
                    </div>
                    <BookInfoList books={content}/>
                    <div align="center">
                        <ManagePaging pageNumber={pageNumber}
                                      totalPages={totalPages}/>
                    </div>
                </form>
            </div>
        )
    }
}

class PagingInfo extends React.Component{
    render() {
        return(
            <b>Страница: {this.props.pageNumber+1} из {this.props.totalPages}</b>
        )
    }
}

class ManagePaging extends React.Component{
    render() {
        var pages
        for (var i=0; i<this.props.totalPages; i++ ) {
            var isCurrent = i===this.props.pageNumber
           pages = <Page is_current_page={isCurrent} number={i}/>
        }
        return(
            <div>
                <div align="center">Страницы:</div>
                {pages}
            </div>
        )
    }
}

class Page extends React.Component{
    render() {
        if (this.props.is_current_page) {
            return(
                <b>{this.props.number}</b>
            )
        } else {
            return(
                <a href="">{this.props.number}</a>
            )
        }
    }
}

class BookInfoList extends React.Component{
    render() {

        if (this.props.books === undefined) {
            return (
                <table className="books">
                    <tbody>
                        <tr>
                            <th>Название</th>
                            <th>Обложка</th>
                            <th>Авторы</th>
                            <th>Жанр</th>
                            <th>Действия</th>
                        </tr>
                    </tbody>
                </table>
            )
        } else {
            const books=this.props.books?.map(book =>
                    <BookInfo key={book.name} books={book}/>
                );
            return (
                <table className="books">
                    <tbody>
                    <tr>
                        <th>Название</th>
                        <th>Обложка</th>
                        <th>Авторы</th>
                        <th>Жанр</th>
                        <th>Действия</th>
                    </tr>
                    {books}
                    </tbody>
                </table>
            )
        }
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
                <td>
                    <input type="button" value="Просмотр"/>&nbsp;
                    <input type="button" value="Загрузить"/>&nbsp;
                    <input type="button" value="Редактировать"/>&nbsp;
                    <input type="button" value="Удалить"/>
                </td>
            </tr>
        )
    }
}