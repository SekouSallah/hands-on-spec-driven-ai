import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'presentation/screens/main/main_screen.dart';
import 'core/theme/app_theme.dart';

final GoRouter _router = GoRouter(
  initialLocation: '/',
  routes: [
    GoRoute(
      path: '/',
      builder: (context, state) => const MainScreen(),
    ),
    // Future routes: /login, /transfer, /cards
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
