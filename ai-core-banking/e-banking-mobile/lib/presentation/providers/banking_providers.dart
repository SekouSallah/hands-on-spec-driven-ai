import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/repositories/banking_repository.dart';
import '../../data/models/account.dart';
import '../../data/models/transaction.dart';
import '../../data/models/card_model.dart';

final bankingRepositoryProvider = Provider((ref) => BankingRepository());

final accountsProvider = FutureProvider<List<Account>>((ref) {
  return ref.read(bankingRepositoryProvider).getAccounts();
});

// We take the first account as the main account for MVP
final mainAccountProvider = Provider<Account?>((ref) {
  final accounts = ref.watch(accountsProvider).value;
  if (accounts != null && accounts.isNotEmpty) {
    return accounts.first;
  }
  return null;
});

final transactionsProvider = FutureProvider<List<Transaction>>((ref) {
  final account = ref.watch(mainAccountProvider);
  if (account != null) {
    return ref.read(bankingRepositoryProvider).getTransactions(account.id);
  }
  return Future.value([]);
});

final cardsProvider = FutureProvider<List<CardModel>>((ref) {
  final account = ref.watch(mainAccountProvider);
  if (account != null) {
    return ref.read(bankingRepositoryProvider).getCards(account.id);
  }
  return Future.value([]);
});
