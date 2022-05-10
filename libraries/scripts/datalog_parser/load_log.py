import os

import collections

from .datalog import DataLogReader
import pandas as pd
import mmap
import argparse


class DataContainer:
    def __init__(self):
        self.timestamps = []
        self.data = []


def get_data(entry, record):
    if entry.type == "double":
        return record.getDouble()
    elif entry.type == "int64":
        return record.getInteger()
    elif entry.type == "string" or entry.type == "json":
        return record.getString()
    elif entry.type == "boolean":
        return record.getBoolean()
    elif entry.type == "boolean[]":
        arr = record.getBooleanArray()
        return arr
    elif entry.type == "double[]":
        arr = record.getDoubleArray()
        return arr
    elif entry.type == "float[]":
        arr = record.getFloatArray()
        return arr
    elif entry.type == "int64[]":
        arr = record.getIntegerArray()
        return arr
    elif entry.type == "string[]":
        arr = record.getStringArray()
        return arr


def load_log(file):

    with open(file, "r") as f:
        mm = mmap.mmap(f.fileno(), 0, access=mmap.ACCESS_READ)
        reader = DataLogReader(mm)

    entries = {}
    data_map = collections.defaultdict(DataContainer)

    for record in reader:
        timestamp = record.timestamp / 1000000
        if record.isStart():
            data = record.getStartData()
            if data.entry in entries:
                print("...DUPLICATE entry ID, overriding")
            entries[data.entry] = data
        elif record.isFinish():
            print("Is finish", record)
            raise Exception("Unsupported")
        elif record.isSetMetadata():
            print("Is metadata", record)
            raise Exception("Unsupported")
        elif record.isControl():
            print("Is control", record)
            raise Exception("Unsupported")
        else:
            entry = entries[record.entry]

            data_map[entry.name].timestamps.append(timestamp)
            data_map[entry.name].data.append(get_data(entry, record))

    series = {}
    for key in data_map:
        s = pd.Series(data_map[key].data, index=data_map[key].timestamps, name=key)
        series[key] = s

    return series
