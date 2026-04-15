package com.ncm.hrms.specification;

import org.springframework.data.jpa.domain.Specification;
import com.ncm.hrms.entity.Employee;

public class EmployeeSpecification {

	public static Specification<Employee> filterEmployees(String name, String phone, String email, String status) {
		return (root, query, cb) -> {

			var predicates = cb.conjunction();

			if (name != null && !name.isEmpty()) {
				predicates = cb.and(predicates, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
			}

			if (phone != null && !phone.isEmpty()) {
				predicates = cb.and(predicates, cb.like(root.get("phoneNumber"), "%" + phone + "%"));
			}

			if (email != null && !email.isEmpty()) {
				predicates = cb.and(predicates, cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
			}

//            if (status != null && !status.isEmpty()) {
//                predicates = cb.and(predicates,
//                        cb.equal(root.get("status"), status));
//            }

			return predicates;
		};
	}

	public static Specification<Employee> globalSearch(String search) {

		return (root, query, cb) -> {

			if (search == null || search.trim().isEmpty()) {
				return cb.conjunction();
			}

			String likePattern = "%" + search.toLowerCase() + "%";

			return cb.or(cb.like(cb.lower(root.get("name")), likePattern),
					cb.like(cb.lower(root.get("email")), likePattern), cb.like(root.get("phoneNumber"), likePattern)
//                    cb.equal(root.get("status"), EmpStatus.valueOf(search.toUpperCase()))

			);
		};
	}
}