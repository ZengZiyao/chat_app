import 'package:frontend/model/person.dart';

class Message {
  int id;
  DateTime createTime;
  Person author;
  String content;

  Message.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    createTime = DateTime.parse(json['createTime']);
    author = Person.fromJson(json);
    content = json['content'];
  }

}