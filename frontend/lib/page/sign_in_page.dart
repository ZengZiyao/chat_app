import 'package:flutter/material.dart';
import 'package:frontend/page/home_page.dart';

class SignInPage extends StatefulWidget {
  @override
  _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
  @override
  Widget build(BuildContext context) {
    return Material(
      child: ListView(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 120),
            width: 80,
            height: 80,
            child: Image.asset(
              'assets/images/wechat_logo.png',
              fit: BoxFit.contain,
            ),
          ),
          SizedBox(height: 40),
       Padding(
         padding: EdgeInsets.symmetric(horizontal: 30),
         child:    Form(
              child: Column(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.symmetric(vertical: 8),
                child: TextFormField(
                  decoration: InputDecoration(
                    prefixIcon: Icon(
                      Icons.account_circle,
                      size: 40,
                    ),
                    hintText: 'Username',
                  ),
                  style: TextStyle(color: Colors.black),
                ),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 8),
                child: TextFormField(
                  decoration: InputDecoration(
                    prefixIcon: Icon(Icons.lock, size: 40),
                    hintText: 'Password',
                  ),
                  obscureText: true,
                  style: TextStyle(color: Colors.black),
                ),
              ),
              FlatButton(
                  onPressed: () => Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => HomePage())),
                    child: Container(
                      width: MediaQuery.of(context).size.width,
                      alignment: Alignment.center,
                      margin: EdgeInsets.symmetric(vertical: 15),
                      padding: EdgeInsets.symmetric(vertical: 12),
                        color: Colors.green,
                        child: Text('Sign In',
                            style: TextStyle(color: Colors.white))),
                  ),

InkWell(
  onTap: null,
  child: Text('Sign Up', style: TextStyle(color: Colors.green, decoration: TextDecoration.underline))
)

            ],
          )),
       ),
        ],
      ),
    );
  }
}
