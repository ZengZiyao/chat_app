class User {
  int id;
  String username;
  String password;

  User.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    password = json['password'];
  }
}