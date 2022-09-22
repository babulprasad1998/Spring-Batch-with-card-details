package com.springbatch.readfile.config;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.springbatch.readfile.model.CardDetails;

@Component
public class CardDetailsProcessor implements ItemProcessor<CardDetails, CardDetails> {

	private List<String> fileName;

	public List<String> getFileName() {
		return fileName;
	}

	public void setFileName(List<String> fileName) {
		this.fileName = fileName;
	}

	@Override
	public CardDetails process(CardDetails cardDetails) throws Exception {

		try {

			System.out.println("Card Validations was called " + cardDetails);

			if (cardDetails.getCustomerName() == null || cardDetails.getCustomerName().length() == 1
					|| cardDetails.getCustomerName().length() > 50) {
				throw new Exception("Invalid Custormer Name");
			}

			if (cardDetails.getCardNumber().length() == 1 || cardDetails.getCardNumber().length() > 10) {
				throw new Exception("Invalid Card Number");

			}

			if (cardDetails.getMobileNumber() == null || cardDetails.getMobileNumber().length() == 9
					|| cardDetails.getMobileNumber().length() > 30) {
				throw new Exception("Invalid MobileNumber");

			}
			if (cardDetails.getEmail() == null || cardDetails.getEmail().length() == 1
					|| cardDetails.getEmail().length() > 50) {
				throw new Exception("Invalid EMail ID");
			}

		} catch (Exception e) {

			return null;
		}

		return cardDetails;
	}

}
