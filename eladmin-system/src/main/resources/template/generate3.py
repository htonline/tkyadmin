# -*- coding: utf-8 -*-

import csv
import numpy as np
import pandas as pd
import open3d as o3d
import trimesh
import os
import sys

def generate_3d_coordinates(length, width, height, num_points, output_file):
    x_coords = np.random.uniform(0, length, num_points) + np.random.normal(0, length * 0.05, num_points)
    y_coords = np.random.uniform(0, height, num_points) + np.random.normal(0, width * 0.05, num_points)
    z_coords = np.random.uniform(0, width, num_points) + np.random.normal(0, height * 0.05, num_points)

    with open(output_file, mode='w', newline='') as file:
        writer = csv.writer(file)
        # writer.writerow(['x', 'y', 'z'])  # ��ѡ��д���ͷ
        for x, y, z in zip(x_coords, y_coords, z_coords):
            writer.writerow([x, y, z])

    return output_file

def reconstruct_surface(input_file, output_file):
    data = pd.read_csv(input_file, header=None, names=['x', 'y', 'z'])

    # �������ƶ���
    pcd = o3d.geometry.PointCloud()
    pcd.points = o3d.utility.Vector3dVector(data[['x', 'y', 'z']].values)

    # ���������ؽ�
    pcd.estimate_normals(search_param=o3d.geometry.KDTreeSearchParamHybrid(radius=0.1, max_nn=30))
    poisson_mesh = o3d.geometry.TriangleMesh.create_from_point_cloud_poisson(
        pcd,
        depth=9,           # ���ư˲�����ȣ�ֵԽ�ߣ�����Խ��ϸ
        width=0,           # ���߳̿�ȣ�ͨ��Ĭ��0��
        scale=1.1,         # ���ű���
        linear_fit=True    # ʹ�������������߾���
    )[0]

    # ��ѡ�������ؽ�������
    bbox = pcd.get_axis_aligned_bounding_box()
    poisson_mesh = poisson_mesh.crop(bbox)

    # �� Open3D �������ת��Ϊ trimesh ��ʽ
    mesh_vertices = np.asarray(poisson_mesh.vertices)
    mesh_faces = np.asarray(poisson_mesh.triangles)
    trimesh_mesh = trimesh.Trimesh(vertices=mesh_vertices, faces=mesh_faces)

    # ʹ�� trimesh ����Ϊ GLB �ļ�
    trimesh.exchange.export.export_mesh(trimesh_mesh, output_file, file_type='glb')
    print(f"GLB saved successfully {output_file}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python script.py <mode> [<length> <width> <height> <num_points> <output_file> | <input_file> <output_file>]")
        sys.exit(1)

    mode = sys.argv[1]

    if mode == "generate":
        if len(sys.argv) != 7:
            print("Usage: python script.py generate <length> <width> <height> <num_points> <output_file>")
            sys.exit(1)
        length = float(sys.argv[2])
        width = float(sys.argv[3])
        height = float(sys.argv[4])
        num_points = int(sys.argv[5])
        output_file = sys.argv[6]
        generated_file = generate_3d_coordinates(length, width, height, num_points, output_file)
        print(f"Coordinates saved to {generated_file}")

    elif mode == "reconstruct":
        if len(sys.argv) != 4:
            print("Usage: python script.py reconstruct <input_file> <output_file>")
            sys.exit(1)
        input_file = sys.argv[2]
        output_file = sys.argv[3]
        reconstruct_surface(input_file, output_file)

    else:
        print("Invalid mode. Use 'generate' or 'reconstruct'")
        sys.exit(1)
