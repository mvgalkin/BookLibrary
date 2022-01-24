const React = require('react');

export class SucceededAuthForm extends React.Component{
    render() {
        return (
            <div id="authform">
                <h1>Добро пожаловать, {this.props.name}!</h1>
            </div>
        )
    }
}

export class RequestAuthForm extends React.Component{
    render() {
        return (
            <div id="authform">
                <form>
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
