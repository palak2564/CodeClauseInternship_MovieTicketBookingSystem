rates = {
    "INR": 1.0,
    "USD": 0.012,
    "EUR": 0.011,
    "GBP": 0.0095,
    "JPY": 1.80,
    "CAD": 0.016
}

cash = float(input("How much money? "))
start_money = input("What type of money you got? (INR/USD/EUR/GBP/JPY/CAD): ").upper()
end_money = input("What type of money you want? (INR/USD/EUR/GBP/JPY/CAD): ").upper()

inr_cash = cash / rates[start_money]
final_cash = inr_cash * rates[end_money]

print(f"{cash} {start_money} is {final_cash:.2f} {end_money}")
