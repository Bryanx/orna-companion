//
//  FileUtil.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 12/04/2021.
//

import Foundation

struct FileUtil {
    enum Error: Swift.Error {
        case fileAlreadyExists
        case invalidDirectory
        case writtingFailed
        case fileNotExists
        case readingFailed
    }
    
    static func write<T: Codable>(_ fileName: String, data: T) {
        guard let url = makeURL(fileName) else {
            fatalError("Couldn't make url")
        }
        print("writeurl: \(url.absoluteString)")
        
        if FileManager.default.fileExists(atPath: url.path) {
            do {
                try FileManager.default.removeItem(atPath: url.path)
            } catch {
                debugPrint(error)
            }
        }
        
        do {
            let encoder = JSONEncoder()
            encoder.keyEncodingStrategy = .convertToSnakeCase
            try encoder.encode(data).write(to: url)
        } catch {
            debugPrint(error)
        }
    }
    
    private static func makeURL(_ fileName: String) -> URL? {
        do {
            let url = try FileManager.default.url(
                    for: .applicationSupportDirectory,
                    in: .userDomainMask,
                    appropriateFor: nil,
                    create: true)
            return url.appendingPathComponent(fileName)
        } catch {
            return nil
        }
    }
    
    static func read<T: Codable>(_ fileName: String) -> T? {
        let data: Data
        
        guard let url = makeURL(fileName) else {
            fatalError("Couldn't make url")
        }
        
        guard FileManager.default.fileExists(atPath: url.path) else {
            return nil
        }
        
        do {
            data = try Data(contentsOf: url)
        } catch {
            fatalError("Couldn't load data from file")
        }
        
        do {
            let decoder = JSONDecoder()
            decoder.keyDecodingStrategy = .convertFromSnakeCase
            return try decoder.decode(T.self, from: data)
        } catch {
            fatalError("Couldn't parse as \(T.self):\n\(error)")
        }
    }
    
    static func update<T: Codable & Identifiable>(_ fileName: String, data: T) {
        guard let items: [T] = read(fileName) else { return }
        var newItems = items.filter { $0.id != data.id}
        newItems.append(data)
        write(fileName, data: newItems)
    }
}
