'use strict';

import {SucceededAuthForm, RequestAuthForm} from './auth/auth';
import {BestBooks} from './books/best_books.js';
import {EditableBooksList} from './books/editable_books.js';

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
    }

    componentDidMount() {
        if (!this.state.isAuthorized) {
            this.loadingBestBooks()
        } else {
            this.loadingAllBooksForPage()
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

    loadingAllBooksForPage(){
        client({method: 'GET', path: root+'/books'}).done(response => {
            this.setState({
                isAuthorized: this.state.isAuthorized,
                bestBooks: this.state.bestBooks,
                pageWithBooks: response.entity
            });
        });
    }

    onAdd(book){
        alert(book);
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
                    <EditableBooksList pageWithBooks={this.state.pageWithBooks} onAdd={this.onAdd}/>
                </div>
            )
        }
    }
}



ReactDOM.render(
    <App />,
    document.getElementById('react')
)
