import 'package:flutter/material.dart';
import 'package:frontend/page/chat_room_page.dart';
import 'package:frontend/page/profile_page.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _currentIndex = 0;
  List<Widget> widgetList = [ChatListPage(), ProfilePage()];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: widgetList.elementAt(_currentIndex),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
              icon: Icon(
                Icons.chat_bubble,
                size: 30,
              ),
              title: Text('Chat')),
          BottomNavigationBarItem(
              icon: Icon(
                Icons.person,
                size: 30,
              ),
              title: Text('Me'))
        ],
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        selectedItemColor: Colors.green,
        unselectedItemColor: Colors.grey,
        elevation: 40,
      ),
    );
  }
}

class ChatListPage extends StatefulWidget {
  @override
  _ChatListPageState createState() => _ChatListPageState();
}

class _ChatListPageState extends State<ChatListPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFEDEDED),
      appBar: AppBar(
        backgroundColor: Color(0xFFEDEDED),
        elevation: 0,
      title: Text('WeChat', style: TextStyle(color: Colors.black)),
      brightness: Brightness.light,),
      body: ListView.separated(
        itemBuilder: (BuildContext context, int index) {
          return _chatRoomCard('chatRoom1');
          }, 
        itemCount: 2, 
        separatorBuilder: (BuildContext context, int index) {
          return Divider(
            indent: 40,
            color: Colors.grey,
            height: 0.2,
          );
          },),
    );
  }

  Widget _chatRoomCard(String name) {
    return InkWell(
      onTap: () => Navigator.push(context, MaterialPageRoute(builder: (context) => ChatRoomPage())), 
      child: Container(
      padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
      color: Colors.white,
      child: Text(name, style: TextStyle(fontSize: 18),),
    ),
    );
  }
}
