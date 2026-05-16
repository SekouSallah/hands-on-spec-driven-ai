import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'presentation/screens/main/main_screen.dart';
import 'presentation/screens/login/login_screen.dart';
import 'core/theme/app_theme.dart';

final GoRouter _router = GoRouter(
  initialLocation: '/login',
  redirect: (context, state) async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('jwt_token');
    
    final isLoggingIn = state.matchedLocation == '/login';
    
    if (token == null && !isLoggingIn) return '/login';
    if (token != null && isLoggingIn) return '/home';
    
    return null;
  },
  routes: [
    GoRoute(
      path: '/login',
      builder: (context, state) => const LoginScreen(),
    ),
    GoRoute(
      path: '/home',
      builder: (context, state) => const MainScreen(),
    ),
  ],
);

class EBankingApp extends StatelessWidget {
  const EBankingApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'AI Core Banking',
      theme: AppTheme.lightTheme,
      routerConfig: _router,
      debugShowCheckedModeBanner: false,
    );
  }
}
