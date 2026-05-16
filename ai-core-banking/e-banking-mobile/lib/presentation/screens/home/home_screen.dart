import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../providers/banking_providers.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final accountAsync = ref.watch(accountsProvider);
    final transactionsAsync = ref.watch(transactionsProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Welcome Back'),
        actions: [
          IconButton(icon: const Icon(Icons.notifications), onPressed: () {}),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: () async {
          ref.invalidate(accountsProvider);
          ref.invalidate(transactionsProvider);
        },
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('Total Balance', style: TextStyle(fontSize: 18, color: Colors.grey)),
              const SizedBox(height: 8),
              accountAsync.when(
                data: (accounts) {
                  if (accounts.isEmpty) return const Text('No active accounts', style: TextStyle(fontSize: 24));
                  return Text(
                    '\$${accounts.first.balance.toStringAsFixed(2)}',
                    style: const TextStyle(fontSize: 36, fontWeight: FontWeight.bold),
                  );
                },
                loading: () => const CircularProgressIndicator(),
                error: (err, stack) => Text('Error: $err', style: const TextStyle(color: Colors.red)),
              ),
              const SizedBox(height: 32),
              const Text('Recent Transactions', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              const SizedBox(height: 16),
              transactionsAsync.when(
                data: (transactions) {
                  if (transactions.isEmpty) return const Text('No recent transactions');
                  return ListView.builder(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    itemCount: transactions.length,
                    itemBuilder: (context, index) {
                      final tx = transactions[index];
                      final isDebit = tx.amount < 0 || tx.type == 'WITHDRAWAL';
                      return ListTile(
                        leading: CircleAvatar(
                          backgroundColor: const Color(0xFFE8EAF6),
                          child: Icon(isDebit ? Icons.arrow_upward : Icons.arrow_downward, color: const Color(0xFF1A237E)),
                        ),
                        title: Text(tx.description ?? tx.reference),
                        subtitle: Text(tx.createdAt.substring(0, 10)),
                        trailing: Text(
                          '${isDebit ? '' : '+'}\$${tx.amount.abs().toStringAsFixed(2)}',
                          style: TextStyle(fontWeight: FontWeight.bold, color: isDebit ? Colors.red : Colors.green),
                        ),
                      );
                    },
                  );
                },
                loading: () => const Center(child: CircularProgressIndicator()),
                error: (err, stack) => Text('Error: $err'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
