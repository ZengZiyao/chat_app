import 'package:flutter/material.dart';

class ChatRoomPage extends StatefulWidget {
  @override
  _ChatRoomPageState createState() => _ChatRoomPageState();
}

class _ChatRoomPageState extends State<ChatRoomPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color(0xFFEDEDED),
        appBar: AppBar(
          brightness: Brightness.light,
          iconTheme: IconThemeData(color: Colors.black),
          title: Text(
            'Chat Room Name',
            style: TextStyle(color: Colors.black),
          ),
          backgroundColor: Color(0xFFEDEDED),
        ),
        body: Column(children: <Widget>[
          // Expanded(
          //   child: ListView.builder(itemBuilder: null),
          // ),
          Container(
            color: Colors.white70,
            constraints: BoxConstraints(maxHeight: 130),
            padding: EdgeInsets.symmetric(horizontal: 15, vertical: 10),
            child: TextFormField(
              style: TextStyle(color: Colors.black),
              decoration: InputDecoration(
                  fillColor: Colors.white,
                  filled: true,
                  border: InputBorder.none),
              keyboardType: TextInputType.text,
              maxLines: 5,
              minLines: 1,
            ),
          )
        ]));
  }
}
