
import '../../core/network/api_client.dart';
import '../models/account.dart';
import '../models/transaction.dart';
import '../models/card_model.dart';

class BankingRepository {
  final ApiClient _apiClient = ApiClient();

  // MOCK: using fixed customer ID 1 for workshop
  final int _customerId = 1;

  Future<List<Account>> getAccounts() async {
    try {
      final response = await _apiClient.dio.get('/accounts/customer/$_customerId');
      if (response.data is List) {
        return (response.data as List).map((json) => Account.fromJson(json)).toList();
      }
      return [];
    } catch (e) {
      throw Exception('Failed to load accounts: $e');
    }
  }

  Future<List<Transaction>> getTransactions(int accountId) async {
    try {
      final response = await _apiClient.dio.get('/transactions/account/$accountId');
      if (response.data['content'] != null) {
        return (response.data['content'] as List)
            .map((json) => Transaction.fromJson(json))
            .toList();
      }
      return [];
    } catch (e) {
      throw Exception('Failed to load transactions: $e');
    }
  }

  Future<List<CardModel>> getCards(int accountId) async {
    try {
      final response = await _apiClient.dio.get('/cards/account/$accountId');
      if (response.data is List) {
        return (response.data as List).map((json) => CardModel.fromJson(json)).toList();
      }
      return [];
    } catch (e) {
      throw Exception('Failed to load cards: $e');
    }
  }

  Future<void> executeTransfer(int sourceAccountId, int targetAccountId, double amount, String description) async {
    try {
      await _apiClient.dio.post('/transactions/transfer', data: {
        'sourceAccountId': sourceAccountId,
        'targetAccountId': targetAccountId,
        'amount': amount,
        'description': description
      });
    } catch (e) {
      throw Exception('Transfer failed: $e');
    }
  }
}
