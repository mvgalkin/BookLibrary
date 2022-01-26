const React = require('react');

export class SucceededAuthForm extends React.Component{
    render() {
        return (
            <div id="authform">
                <form action="/logout" method="POST">
                    <h1>Добро пожаловать, {this.props.userName}!</h1>
                    <input type="submit" value={"Выйти"}/>
                </form>
            </div>
        )
    }
}

export class RequestAuthForm extends React.Component{
    render() {
        return (
            <div id="authform">
                <form action="/login" method="POST">
                    <h1>Добро пожаловать, пожалуйста авторизуйтесь</h1>
                    <table>
                        <tbody>
                            <tr>
                                <th>Имя пользователя</th>
                                <td><input type='text' id='username' name='username'/></td>
                            </tr>
                            <tr>
                                <th>Пароль:</th>
                                <td><input type='password' id='password' name='password'/></td>
                            </tr>
                            <tr>
                                <td colSpan='2'><input type='submit' /></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        )
    }
}
