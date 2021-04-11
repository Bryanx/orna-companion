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
        specializations = loadFile("SpecializationResponse.json")
        isLoading = false
        
        // should fetch from local db here.
        // only fetch from network if its empty
        
        if !specializations.isEmpty {
            return
        }
        
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
        let specializations: [Specialization] = loadFile("SpecializationResponse.json")
        specialization = specializations.first(where: { $0.id == id }) ?? Specialization()
        isLoading = false
//        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
//        print("SpecializationViewModel: bla")
//
//        let json: [String:Any] = ["id":id]
//        do {
//            let data = try JSONSerialization.data(withJSONObject: json, options: [])
//            var request = URLRequest(url: url)
//            request.httpMethod = "POST"
//            request.httpBody = data
//            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//            request.addValue("application/json", forHTTPHeaderField: "Accept")
//
//            URLSession.shared.dataTask(with: request) { (data, response, error) in
//                print("SpecializationViewModel: got here")
//                guard let dataResp = data else { return }
//                let decoder: JSONDecoder = JSONDecoder()
//                decoder.keyDecodingStrategy = .convertFromSnakeCase
//                guard let specialization = try? decoder.decode([Specialization].self, from: dataResp).first else { return }
//
//                print("SpecializationViewModel: json2 \(specialization)")
//                DispatchQueue.main.async {
//                    self.specialization = specialization
//                    self.isLoading = false
//                }
//            }.resume()
//        } catch {
//            print("SpecializationViewModel: error")
//            self.isLoading = false
//        }
    }
}

func loadFile<T: Decodable>(_ filename: String) -> T {
    let data: Data

    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else {
            fatalError("Couldn't find \(filename) in main bundle.")
    }

    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }

    do {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
