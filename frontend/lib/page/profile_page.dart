import 'package:flutter/material.dart';

class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        brightness: Brightness.light,
        backgroundColor: Color(0xFFEDEDED),
        elevation: 0,
      ),
      body: Column(children: <Widget>[
        Container(
            color: Colors.white,
            padding: EdgeInsets.fromLTRB(30, 10, 20, 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Image.asset(
                  'assets/images/avatar.jpg',
                  width: 65,
                  height: 65,
                  fit: BoxFit.cover,
                ),
                Padding(
                    padding: EdgeInsets.only(left: 10),
                    child: Text(
                      'username',
                      style: TextStyle(fontSize: 18, color: Colors.black),
                    ))
              ],
            )),
        Divider(
          height: 10,
        ),
        Container(
            color: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(Icons.settings, size: 30, color: Colors.grey),
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(left: 10),
                    child: Text(
                      'Settings',
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                ),
                Icon(Icons.arrow_right, size: 30, color: Colors.grey)
              ],
            ))
      ]),
    );
  }
}
