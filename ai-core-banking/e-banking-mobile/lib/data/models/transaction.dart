class Transaction {
  final int id;
  final String reference;
  final double amount;
  final String type;
  final String status;
  final String? description;
  final String createdAt;

  Transaction({
    required this.id,
    required this.reference,
    required this.amount,
    required this.type,
    required this.status,
    this.description,
    required this.createdAt,
  });

  factory Transaction.fromJson(Map<String, dynamic> json) {
    return Transaction(
      id: json['id'],
      reference: json['reference'],
      amount: (json['amount'] as num).toDouble(),
      type: json['type'],
      status: json['status'],
      description: json['description'],
      createdAt: json['createdAt'],
    );
  }
}
