import 'package:frontend/model/person.dart';

class ChatRoom {
  int id;
  String name;
  int passcode;
  Person author;
  List<Person> memebers;

  ChatRoom.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    passcode = json['passcode'];
    author = Person.fromJson(json);
    memebers =   (json['members'] as List<Map<String, dynamic>>).map((e) => Person.fromJson(e)).toList();
  }
}