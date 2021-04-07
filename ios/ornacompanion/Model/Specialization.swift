//
//  Item.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation

struct Specialization: Hashable, Codable, Identifiable {
    var id: Int
    var name: String
    var images: [String]
    
    func getImage() -> String {
        if images.count == 0 {
          return ""
        } else {
          return Constant.ORNA_IMAGE_PREFIX + images[images.count - 1]
        }
    }
}
