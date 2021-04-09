//
//  SpecializationViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation
import SwiftUI

public class SpecializationViewModel: ObservableObject {
    @Published var specializations = [Specialization]()
    @Published var isLoading = true

    func fetchSpecializations() {
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }

        let json: [String:Any] = [:]
        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")

            URLSession.shared.dataTask(with: request) { (data, response, error) in
                print("SpecializationViewModel: got here")
                guard let dataResp = data else { return }
                guard let specializations = try? JSONDecoder().decode([Specialization].self, from: dataResp) else { return }

                print("SpecializationViewModel: json2 \(specializations)")
                DispatchQueue.main.async {
                    self.specializations = specializations
                    self.isLoading = false
                }
            }.resume()
        } catch {
            print("SpecializationViewModel: error")
            self.isLoading = false
        }
    }
}

public class SpecializationDetailViewModel: ObservableObject {
    @Published var specialization = Specialization()
    @Published var isLoading = true

    func fetchSpecialization(id: Int) {
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        print("SpecializationViewModel: bla")

        let json: [String:Any] = ["id":id]
        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")

            URLSession.shared.dataTask(with: request) { (data, response, error) in
                print("SpecializationViewModel: got here")
                guard let dataResp = data else { return }
                guard let specialization = try? JSONDecoder().decode([Specialization].self, from: dataResp).first else { return }

                print("SpecializationViewModel: json2 \(specialization)")
                DispatchQueue.main.async {
                    self.specialization = specialization
                    self.isLoading = false
                }
            }.resume()
        } catch {
            print("SpecializationViewModel: error")
            self.isLoading = false
        }
    }
}
