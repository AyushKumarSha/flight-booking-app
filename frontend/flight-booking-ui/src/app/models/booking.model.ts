export interface BookingRequest {
  userId: number;
  routeId: number;
  travelDate: string;
  paymentMode: string;
  cardNumber?: string;
  cardExpiry?: string;
  cardCvv?: string;
}

export interface BookingResponse {
  bookingId: number;
  userId: number;
  flightNumber: string;
  source: string;
  destination: string;
  travelDate: string;
  amount: number;
  paymentMode: string;
  status: string;
  createdAt: string;
}

export interface PaymentResponse {
  id: number;
  bookingId: number;
  userId: number;
  amount: number;
  paymentMode: string;
  status: string;
  transactionId: string;
  failureReason: string;
  createdAt: string;
}