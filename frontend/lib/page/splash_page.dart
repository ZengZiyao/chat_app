import 'package:flutter/material.dart';
import 'package:frontend/page/sign_in_page.dart';

class SplashPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (context) => SignInPage())),
      child: Container(
        child: Image.asset('assets/images/splash_image.jpg'),
      ),
    );
  }
}
