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
        this.state = {isAuthorized: true, best_books: [], paged_books: {}};
    }

    componentDidMount() {
        if (!this.state.isAuthorized) {
            client({method: 'GET', path: root+'/best_books'}).done(response => {
                var newState = this.state;
                newState.best_books = response.entity
                this.setState(newState);
            });
        } else {
            client({method: 'GET', path: root+'/books'}).done(response => {
                var newState = this.state;
                newState.paged_books = response.entity
                this.setState(newState);
            });
        }
    }

    render() {
        if (!this.state.isAuthorized) {
            return (
                <div>
                    <RequestAuthForm/>
                    <br/><br/>
                    <BestBooks books={this.state.best_books}/>
                </div>
            )
        } else {
            return (
                <div>
                    <SucceededAuthForm/>
                    <br/><br/>
                    <EditableBooksList paged_books={this.state.paged_books}/>
                </div>
            )
        }
    }
}



ReactDOM.render(
    <App />,
    document.getElementById('react')
)
