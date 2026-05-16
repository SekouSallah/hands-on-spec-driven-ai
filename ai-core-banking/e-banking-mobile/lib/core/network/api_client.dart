import 'package:dio/dio.dart';
import '../constants/app_constants.dart';
import 'interceptors/auth_interceptor.dart';

class ApiClient {
  late final Dio dio;

  ApiClient() {
    dio = Dio(BaseOptions(
      baseUrl: AppConstants.baseUrl,
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 10),
      contentType: 'application/json',
    ));

    dio.interceptors.add(AuthInterceptor());
    dio.interceptors.add(LogInterceptor(responseBody: true, requestBody: true));
  }
}
