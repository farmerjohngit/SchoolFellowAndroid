/**
* Sample React Native App
* https://github.com/facebook/react-native
*/

import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  TouchableHighlight,
  Alert,
  TextInput,
  ToolbarAndroid,
  NativeModules,
  View
} from 'react-native';
var login= NativeModules.Login;

class LoginComponent extends Component {
  constructor(props) {
    super(props);
    this.acc='';
    this.pwd='';
  }
  render() {
    return (
     <View style={styles.container}>

     <View style={styles.verticalFlow}>
     <View style={ {flexDirection: 'row',borderBottomWidth: 1,borderColor:'#f1f1f1',paddingTop:4,paddingBottom:4}}>

     <TouchableHighlight  style={{flex: 1,marginLeft:16,alignSelf: 'center'}}
     >
     <Text  style={{
      fontSize: 18,
      color: 'black',
      alignSelf: 'flex-start'
    }} ></Text>
    </TouchableHighlight>

    <Text  style={{
      flex: 1,
      fontSize: 22,
      color: 'black',
      textAlign: 'center',
      justifyContent:'center',
      alignSelf: 'center'}} >登录</Text>

      <TouchableHighlight  style={{flex: 1,marginRight:16,alignSelf: 'center'}}
      onPress={this.onRegiPressed.bind(this)}
      >
      <Text  style={{
        fontSize: 18,
        color: 'black',
        alignSelf: 'flex-end'
      }} >注册</Text>
      </TouchableHighlight>

      </View>


      <View style={ {flexDirection: 'row',flex:1, alignSelf: 'stretch',
      justifyContent: 'center'}}>
      <Text  style={[styles.normalText,{marginLeft:12}]} >账号： </Text>
      <TextInput
      style={styles.searchInput}
      placeholder='输入你的账号'
      onChangeText={(text) => this.acc=text}
      />
      </View>
      <View style={ {flexDirection: 'row',flex:1, alignSelf: 'stretch',
      justifyContent: 'center'}}>
      <Text  style={[styles.normalText,{marginLeft:12}]} >密码： </Text>
      <TextInput
      style={styles.searchInput}
      secureTextEntry={true} 
      placeholder='输入你的密码'
      onChangeText={(text) => this.pwd=text}
      />
      </View>

      <TouchableHighlight  style={styles.button}
      underlayColor='#99d9f4'
      onPress={this.onLoginPressed.bind(this)}>
      <Text  style={styles.buttonText} >登录</Text>
      </TouchableHighlight>

      </View>
      <View style={ {marginTop:200,flexDirection: 'row',backgroundColor:"white",  alignSelf: 'stretch'}}>
      <Text style={{ marginLeft:12 ,alignSelf: 'flex-start',flex:1}}>忘记密码？</Text>
      <Text style={{marginRight:12,alignSelf: 'flex-end',flex:1,textAlign:'right'}}>遇到问题</Text>
      </View>
      </View>
      );
  }

  onLoginPressed() {
    console.log('acc: '+this.acc);
    login.login(this.acc,this.pwd);
  }
  onRegiPressed(){
    console.log('onRegiPressed: ');
    login.goRegi();
  }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  verticalFlow:{
    justifyContent: 'center',
    backgroundColor: '#FFFFFF',
  },
  searchInput: {
    flex:4,
    height: 36,
    padding: 4,
    marginRight: 30,
    marginTop: 30,
    marginBottom: 30,
    marginLeft: 10,
    fontSize: 18,
    borderWidth: 1,
    borderColor: '#48BBEC',
    borderRadius: 8,
    color: '#48BBEC'
  },
  loginButton:{
    marginRight: 130,
    marginTop: 30,
    marginBottom: 30,
    marginLeft: 30
  },
  normalText: {
    flex:1,
    fontSize: 18,
    color: 'black',
    alignSelf: 'center'
  },
  buttonText: {
    fontSize: 18,
    color: 'white',
    alignSelf: 'center'
  },
  button: {
    height: 36,
    flex: 1,
    backgroundColor: '#48BBEC',
    borderColor: '#48BBEC',
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 10,
    alignSelf: 'stretch',
    justifyContent: 'center'
  },
});

AppRegistry.registerComponent('SchoolFellow_Login', () => LoginComponent);
AppRegistry.registerComponent('SchoolFellow_Reg', () => LoginComponent);
