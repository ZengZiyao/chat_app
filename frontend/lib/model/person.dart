class Person {
  int id;
  String username;

  Person.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
  }
}