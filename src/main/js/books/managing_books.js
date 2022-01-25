import {observer} from "mobx-react"
import {observable} from "mobx";
const React = require('react');
const ReactDOM = require('react-dom');

const authorsCount = observable({
    count:1,
    add(){
        this.count++;
    },
    remove(){
        if (this.count>1) {
            this.count--;
        }
    },
    setDefault(){
        this.count = 1;
    }
})

const genresCount = observable({
    count:1,
    add(){
        this.count++;
    },
    remove(){
        if (this.count>1) {
            this.count--;
        }
    },
    setDefault(){
        this.count = 1;
    }
})

@observer
export class AddDialog extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.addAuthor=this.addAuthor.bind(this);
        this.removeAuthor=this.removeAuthor.bind(this);
        this.addGenre=this.addGenre.bind(this);
        this.removeGenre=this.removeGenre.bind(this);
    }

    @observable genresCount = 1

    handleSubmit(e) {
        e.preventDefault();
        const book = {};

        book.name = ReactDOM.findDOMNode(this.refs["book_name"]).value.trim();
        book.cover = ReactDOM.findDOMNode(this.refs["book_cover"]).value;
        book.content = ReactDOM.findDOMNode(this.refs["book_content"]).value;
        book.authors=[]
        book.genres=[]

        for (let i=0; i<authorsCount.count; i++){
            book.authors.push({
                name: ReactDOM.findDOMNode(this.refs["'author_name" + i + "'"]).value.trim()
            })
        }
        for (let j=0; j<genresCount.count; j++){
            book.genres.push({
                name: ReactDOM.findDOMNode(this.refs["'genre_name"+j+"'"]).value.trim()
            })
        }

        console.warn(book);

        this.props.onAdd(book);

        ReactDOM.findDOMNode(this.refs["book_name"]).value="";
        ReactDOM.findDOMNode(this.refs["book_cover"]).value="";
        ReactDOM.findDOMNode(this.refs["book_content"]).value="";
        authorsCount.setDefault();
        genresCount.setDefault();
        ReactDOM.findDOMNode(this.refs["'author_name0'"]).value="";
        ReactDOM.findDOMNode(this.refs["'genre_name0'"]).value="";

        // Navigate away from the dialog to hide it.
        window.location = "#";
    }

    addAuthor(e){
        e.preventDefault()
        authorsCount.add();
    }

    removeAuthor(e){
        e.preventDefault()
        authorsCount.remove()
    }

    addGenre(e){
        e.preventDefault()
        genresCount.add()
    }

    removeGenre(e){
        e.preventDefault()
        genresCount.remove()
    }

    showDialog(e){
        e.preventDefault()
        window.location="#addBook";
    }

    render() {
        const authorInputs = [];
        for(let i = 0; i<authorsCount.count; i++ ) {
            authorInputs.push(
                <p key={"'author"+i+"'"}>
                    <input type="text" placeholder="имя" ref={"'author_name"+i+"'"} className="field"/>
                </p>
            );
        }

        const genreInputs = [];
        for(let j = 0; j<genresCount.count; j++ ) {
            genreInputs.push(
                <p key={"'genre"+j+"'"}>
                    <input type="text" placeholder="имя" ref={"'genre_name"+j+"'"} className="field"/>
                </p>
            );
        }

        return (
            <div>
                <input type="button" value="Добавить" onClick={this.showDialog}/>
                <div id="addBook" className="modalDialog">
                    <div>
                        <a href="#" title="Close" className="close">X</a>

                        <h2>Add new book</h2>

                            <input type="text" placeholder="Название" ref="book_name" className="field"/><br/><br/>
                            <label>Обложка:</label><br/>
                            <input type="file" placeholder="Обложка" ref="book_cover" className="field"/><br/>
                            <label>Книжка(pdf,ebuk и т.п.):</label><br/>
                            <input type="file" placeholder="Книжка(pdf,ebuk и т.п.)" ref="book_content" className="field"/>
                            <br/>
                            <label>Жанры:</label><input type="button" value="+" onClick={this.addGenre}/><input type="button" value="-" onClick={this.removeGenre}/>
                            {genreInputs}

                            <br/>
                            <label>Авторы:</label><input type="button" value="+" onClick={this.addAuthor}/><input type="button" value="-" onClick={this.removeAuthor}/>
                            {authorInputs}

                            <button onClick={this.handleSubmit}>Добавить</button>
                    </div>
                </div>
            </div>
        )
    }

}