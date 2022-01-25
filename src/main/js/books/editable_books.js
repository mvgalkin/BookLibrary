import {AddDialog,EditDialog} from "./managing_books";

const React = require("react");

export class EditableBooksList extends React.Component{

    render() {
        var pageNumber = this.props.pageWithBooks?.pageable?.pageNumber
        if (pageNumber == null) {
            pageNumber = 0
        }
        var totalPages = this.props.pageWithBooks?.totalPages
        if (totalPages == null) {
            totalPages = 1
        }
        const content = this.props.pageWithBooks.content

        return (
            <div>
                <h2>Наша библиотека:</h2>
                <form>
                    <div align="right">
                        <PagingInfo pageNumber={pageNumber}
                                    totalPages={totalPages}/>
                    </div>
                    <AddDialog onAdd={this.props.onAdd}/>
                    <BookInfoList books={content} onView={this.props.onView} onDownload={this.props.onDownload} onEdit={this.props.onEdit} onDelete={this.props.onDelete}/>
                    <div align="center">
                        <ManagePaging pageNumber={pageNumber}
                                      totalPages={totalPages}
                                        onNavigateToPage={this.props.onNavigateToPage}/>
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
           pages = <Page is_current_page={isCurrent} number={i} onNavigateToPage={this.props.onNavigateToPage}/>
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
                <a href="" onClick={(e)=>{this.props.onNavigateToPage(e, this.props.number)}}>{this.props.number}</a>
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
                    <BookInfo key={book.name} book={book} onView={this.props.onView} onDownload={this.props.onDownload} onEdit={this.props.onEdit} onDelete={this.props.onDelete}/>
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
                <td>{this.props.book.name}</td>
                <td>{this.props.book.cover}</td>
                <td>{this.props.book.authors.map(a => a.name).join(', ')}</td>
                <td>{this.props.book.genres.map(g => g.name).join(', ')}</td>
                <td>
                    <input type="hidden" value={this.props.book.id}/>
                    <input type="button" value="Просмотр" onClick={(e)=>{this.props.onView(e,this.props.book.id)}}/>&nbsp;
                    <input type="button" value="Загрузить" onClick={(e)=>{this.props.onDownload(e,this.props.book.id)}}/>&nbsp;
                    <EditDialog book={this.props.book} onEdit={this.props.onEdit}/>&nbsp;
                    <input type="button" value="Удалить" onClick={(e)=>{this.props.onDelete(e,this.props.book.id)}}/>
                </td>
            </tr>
        )
    }
}