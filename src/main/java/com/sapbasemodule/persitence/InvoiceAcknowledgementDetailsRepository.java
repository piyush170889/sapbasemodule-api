package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.InvoicesAcknowledgementDetails;

@Repository
public interface InvoiceAcknowledgementDetailsRepository
		extends JpaRepository<InvoicesAcknowledgementDetails, Integer> {

	InvoicesAcknowledgementDetails findByInvoiceNo(int invoiceNo);

	@Query("select iad from InvoicesAcknowledgementDetails iad where iad.invoiceNo IN ?1")
	List<InvoicesAcknowledgementDetails> selectByInvoiceNos(List<Integer> invoiceDocEntriesList);

}
