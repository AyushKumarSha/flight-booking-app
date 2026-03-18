export interface FlightSearchResponse {
  routeId: number;
  flightNumber: string;
  aircraftName: string;
  airline: string;
  source: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  availableSeats: number;
  basePrice: number;
  dynamicPrice: number;
}