class CardModel {
  final int id;
  final String maskedPan;
  final String expiryDate;
  final String status;
  final String cardType;

  CardModel({
    required this.id,
    required this.maskedPan,
    required this.expiryDate,
    required this.status,
    required this.cardType,
  });

  factory CardModel.fromJson(Map<String, dynamic> json) {
    return CardModel(
      id: json['id'],
      maskedPan: json['maskedPan'],
      expiryDate: json['expiryDate'],
      status: json['status'],
      cardType: json['cardType'],
    );
  }
}
