//
//  IoUtil.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 12/04/2021.
//

import Foundation

struct IoUtil {
    static func loadResourceFile<T: Decodable>(_ filename: String) -> T {
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
    
    static func loadFromNetwork<T : Decodable>(
        _ url: URL,
        _ body: [String:Any] = [:],
        onSuccess:  @escaping (T) -> Void,
        onError: () -> Void
    ) {
        do {
            let data = try JSONSerialization.data(withJSONObject: body, options: [])
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            
            URLSession.shared.dataTask(with: request) { (data, response, error) in
                guard let dataResp = data else { return }
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                guard let result = try? decoder.decode(T.self, from: dataResp) else { return }
                DispatchQueue.main.async {
                    onSuccess(result)
                }
            }.resume()
        } catch {
            onError()
        }
    }
}
