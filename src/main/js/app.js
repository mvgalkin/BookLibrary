'use strict';

import {SucceededAuthForm, RequestAuthForm} from './auth/auth';
import {BestBooks} from './books/best_books.js';
import {EditableBooksList} from './books/editable_books.js';
import {observable} from "mobx";
import {observer} from "mobx-react";


const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const root='/api'

class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isAuthorized: false,
            isFindBy: "",
            findCaption: "",
            isFindText: "",
            userName: "",
            bestBooks: [],
            pageWithBooks: {}
        };
        this.onNavigateToPage = this.onNavigateToPage.bind(this);
        this.onAdd = this.onAdd.bind(this);
        this.onEdit = this.onEdit.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onDownload = this.onDownload.bind(this);
        this.onView=this.onView.bind(this);
        this.findByName=this.findByName.bind(this);
        this.findByAuthor=this.findByAuthor.bind(this);
        this.findByGenre=this.findByGenre.bind(this);
        this.onCloseFind=this.onCloseFind.bind(this);
        this.currentPageNumber = 0;
    }

    componentDidMount() {
        fetch("/api/login", {headers:{'X-Requested-With': 'XMLHttpRequest'}})
            .then(response=>{
                if (response.status === 200) {
                    if (!this.state.isAuthorized){
                        this.setState({
                            isAuthorized: true,
                            isFindBy: this.state.isFindBy,
                            findCaption: this.state.findCaption,
                            isFindText: this.state.isFindText,
                            userName: this.state.userName,
                            bestBooks: this.state.bestBooks,
                            pageWithBooks: this.state.pageWithBooks
                        });
                    }
                } else {
                    if (this.state.isAuthorized) {
                        this.setState({
                            isAuthorized: false,
                            isFindBy: this.state.isFindBy,
                            findCaption: this.state.findCaption,
                            isFindText: this.state.isFindText,
                            userName: this.state.userName,
                            bestBooks: this.state.bestBooks,
                            pageWithBooks: this.state.pageWithBooks
                        });
                    }
                }
                return response.text()
            })
            .then(data => {
                this.setState({
                    isAuthorized: this.state.isAuthorized,
                    isFindBy: this.state.isFindBy,
                    findCaption: this.state.findCaption,
                    isFindText: this.state.isFindText,
                    userName: data,
                    bestBooks: this.state.bestBooks,
                    pageWithBooks: this.state.pageWithBooks
                });
            })
            .then((data)=>{
                if (!this.state.isAuthorized) {
                    this.loadingBestBooks()
                } else {
                    this.loadingAllBooksForPage(this.currentPageNumber)
                }
            })
            .catch(error=>{
                if (this.state.isAuthorized) {
                    this.setState({
                        isAuthorized: false,
                        isFindBy: this.state.isFindBy,
                        findCaption: this.state.findCaption,
                        isFindText: this.state.isFindText,
                        userName: this.state.userName,
                        bestBooks: this.state.bestBooks,
                        pageWithBooks: this.state.pageWithBooks
                    });
                }
            });
    }

    loadingBestBooks() {
        client({method: 'GET', path: root+'/best_books'}).done(response => {
            console.warn(response.entity)
            this.setState({
                isAuthorized: this.state.isAuthorized,
                isFindBy: "",
                findCaption: "",
                isFindText: this.state.isFindText,
                userName: this.state.userName,
                bestBooks: response.entity,
                pageWithBooks: this.state.pageWithBooks
            });
        });
    }

    loadingAllBooksForPage(pageNumber){
        client({method: 'GET', path: root+'/books?pagenumber='+pageNumber}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                isFindBy: "",
                findCaption: "",
                isFindText: this.state.isFindText,
                userName: this.state.userName,
                bestBooks: this.state.bestBooks,
                pageWithBooks: response.entity
            });
        });
    }

    onAdd(book){
        client({
            method: 'POST',
            path: root+'/books',
            entity: book,
            headers: {'Content-Type': 'application/json'}
        }).done(response => {
            this.loadingAllBooksForPage(this.currentPageNumber)
        });
    }

    onView(e, id){
        e.preventDefault();
        window.location='.'+root+'/books/'+id+'/content'
    }

    onDownload(e, id){
        e.preventDefault();
        window.location='.'+root+'/books/'+id+'/content'
    }

    onDelete(e, id){
        e.preventDefault();
        client({
            method: 'DELETE',
            path: root+'/books/'+id
        }).done(response => {
            this.loadingAllBooksForPage(this.currentPageNumber)
        });
    }

    onEdit(book){
        client({
            method: 'PUT',
            path: root+'/books/'+book.id,
            entity: book,
            headers: {'Content-Type': 'application/json'}
        }).done(response => {
            this.loadingAllBooksForPage(this.currentPageNumber)
        });
    }

    onNavigateToPage(e, pageNumber){
        e.preventDefault();
        this.currentPageNumber = pageNumber
        if (this.state.isFindBy==="") {
            this.loadingAllBooksForPage(pageNumber);
        } else if (this.state.isFindBy==="author") {
            this.findByAuthor(e, this.state.isFindText)
        } else if (this.state.isFindBy==="genre") {
            this.findByGenre(e, this.state.isFindText)
        } else  {
            this.findByName(e, this.state.isFindText)
        }
    }

    findByName(e, name){
        e.preventDefault();
        client({method: 'GET', path: root+'/books/find/name/'+name+'?pagenumber='+this.currentPageNumber}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                isFindBy: "name",
                findCaption: "Результат поиска по Имени: '"+name+"'",
                userName: this.state.userName,
                bestBooks: this.state.bestBooks,
                pageWithBooks: response.entity
            });
        });
    }

    findByAuthor(e, name){
        e.preventDefault();
        client({method: 'GET', path: root+'/books/find/author/'+name+'?pagenumber='+this.currentPageNumber}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                isFindBy: "author",
                findCaption: "Результат поиска по Автору: '"+name+"'",
                userName: this.state.userName,
                bestBooks: this.state.bestBooks,
                pageWithBooks: response.entity
            });
        });
    }

    findByGenre(e, name){
        e.preventDefault();
        client({method: 'GET', path: root+'/books/genre/name/'+name+'?pagenumber='+this.currentPageNumber}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                isFindBy: "genre",
                findCaption: "Результат поиска по Жанру: '"+name+"'",
                userName: this.state.userName,
                bestBooks: this.state.bestBooks,
                pageWithBooks: response.entity
            });
        });
    }

    onCloseFind(e){
        e.preventDefault();
        this.setState({
            isAuthorized: this.state.isAuthorized,
            isFindBy: this.state.isFindBy,
            userName: this.state.userName,
            bestBooks: this.state.bestBooks,
            pageWithBooks: this.state.pageWithBooks
        });
    }

    render() {
        if (!this.state.isAuthorized) {
            return (
                <div>
                    <RequestAuthForm/>
                    <br/><br/>
                    <BestBooks books={this.state.bestBooks}/>
                </div>
            )
        } else {
            return (
                <div>
                    <SucceededAuthForm userName={this.state.userName}/>
                    <br/><br/>
                    <EditableBooksList pageWithBooks={this.state.pageWithBooks} findCaption={this.state.findCaption} findByName={this.findByName} findByAuthor={this.findByAuthor} findByGenre={this.findByGenre} onCloseFind={this.onCloseFind} onAdd={this.onAdd} onView={this.onView} onDownload={this.onDownload} onEdit={this.onEdit} onDelete={this.onDelete} onNavigateToPage={this.onNavigateToPage}/>
                </div>
            )
        }
    }
}



ReactDOM.render(
    <App />,
    document.getElementById('react')
)
