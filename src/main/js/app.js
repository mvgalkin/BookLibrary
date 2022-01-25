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
            isAuthorized: true,
            bestBooks: [],
            pageWithBooks: {}
        };
        this.onNavigateToPage = this.onNavigateToPage.bind(this);
        this.onAdd = this.onAdd.bind(this);
        this.onEdit = this.onEdit.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onDownload = this.onDownload.bind(this);
        this.onView=this.onView.bind(this);
        this.currentPageNumber = 0;
    }

    componentDidMount() {
        if (!this.state.isAuthorized) {
            this.loadingBestBooks()
        } else {
            this.loadingAllBooksForPage(this.currentPageNumber)
        }
    }

    loadingBestBooks() {
        client({method: 'GET', path: root+'/best_books'}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                bestBooks: response.entity,
                pageWithBooks: this.state.pageWithBooks
            });
        });
    }

    loadingAllBooksForPage(pageNumber){
        client({method: 'GET', path: root+'/books?pagenumber='+pageNumber}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
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
        this.loadingAllBooksForPage(pageNumber);
    }

    render() {
        if (!this.state.isAuthorized) {
            return (
                <div>
                    <RequestAuthForm/>
                    <br/><br/>
                    <BestBooks books={this.state.pageWithBooks}/>
                </div>
            )
        } else {
            return (
                <div>
                    <SucceededAuthForm/>
                    <br/><br/>
                    <EditableBooksList pageWithBooks={this.state.pageWithBooks} onAdd={this.onAdd} onView={this.onView} onDownload={this.onDownload} onEdit={this.onEdit} onDelete={this.onDelete} onNavigateToPage={this.onNavigateToPage}/>
                </div>
            )
        }
    }
}



ReactDOM.render(
    <App />,
    document.getElementById('react')
)
