import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../providers/banking_providers.dart';

class TransferScreen extends ConsumerStatefulWidget {
  const TransferScreen({super.key});

  @override
  ConsumerState<TransferScreen> createState() => _TransferScreenState();
}

class _TransferScreenState extends ConsumerState<TransferScreen> {
  final _formKey = GlobalKey<FormState>();
  final _targetAccountController = TextEditingController();
  final _amountController = TextEditingController();
  final _descController = TextEditingController();
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    final mainAccount = ref.watch(mainAccountProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Transfer')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text('From Account ID: ${mainAccount?.id ?? 'Loading...'}', style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
              const SizedBox(height: 24),
              TextFormField(
                controller: _targetAccountController,
                decoration: const InputDecoration(labelText: 'Target Account ID', border: OutlineInputBorder()),
                keyboardType: TextInputType.number,
                validator: (val) => val == null || val.isEmpty ? 'Required' : null,
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _amountController,
                decoration: const InputDecoration(labelText: 'Amount', border: OutlineInputBorder()),
                keyboardType: TextInputType.number,
                validator: (val) => val == null || val.isEmpty ? 'Required' : null,
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _descController,
                decoration: const InputDecoration(labelText: 'Description', border: OutlineInputBorder()),
              ),
              const SizedBox(height: 32),
              ElevatedButton(
                onPressed: _isLoading || mainAccount == null ? null : _submitTransfer,
                child: _isLoading ? const CircularProgressIndicator(color: Colors.white) : const Text('SEND TRANSFER'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _submitTransfer() async {
    if (!_formKey.currentState!.validate()) return;
    
    setState(() => _isLoading = true);
    try {
      final sourceAccountId = ref.read(mainAccountProvider)!.id;
      final targetAccountId = int.parse(_targetAccountController.text);
      final amount = double.parse(_amountController.text);
      
      await ref.read(bankingRepositoryProvider).executeTransfer(
        sourceAccountId, targetAccountId, amount, _descController.text
      );
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Transfer successful!')));
        ref.invalidate(transactionsProvider);
        ref.invalidate(accountsProvider);
        _targetAccountController.clear();
        _amountController.clear();
        _descController.clear();
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString()), backgroundColor: Colors.red));
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }
}
