//
//  SpecializationViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation
import SwiftUI

let TEST_BODY = "{'id':56}"

public class SpecializationViewModel: ObservableObject {
    @Published var specializations = [Specialization]()

    func fetchSpecialization() {
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        print("SpecializationViewModel: bla")

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
                }
            }.resume()
        } catch {
            print("SpecializationViewModel: error")
        }
    }
}
